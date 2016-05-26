package hr.fer.elektrijada.activities.bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import hr.fer.elektrijada.dal.sql.category.CategoryFromDb;
import hr.fer.elektrijada.dal.sql.category.SqlCategoryRepository;
import hr.fer.elektrijada.dal.sql.competition.CompetitionFromDb;
import hr.fer.elektrijada.dal.sql.competition.SqlCompetitionRepository;
import hr.fer.elektrijada.dal.sql.competitionscore.CompetitionScoreFromDb;
import hr.fer.elektrijada.dal.sql.competitionscore.SqlCompetitionScoreRepository;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.dal.sql.duel.DuelFromDb;
import hr.fer.elektrijada.dal.sql.duel.SqlDuelRepository;
import hr.fer.elektrijada.dal.sql.duelscore.DuelScoreFromDb;
import hr.fer.elektrijada.dal.sql.duelscore.SqlDuelScoreRepository;
import hr.fer.elektrijada.dal.sql.faculty.FacultyFromDb;
import hr.fer.elektrijada.dal.sql.faculty.SqlFacultyRepository;
import hr.fer.elektrijada.dal.sql.news.NewsFromDb;
import hr.fer.elektrijada.dal.sql.news.SqlNewsRepository;
import hr.fer.elektrijada.dal.sql.stage.SqlStageRepository;
import hr.fer.elektrijada.dal.sql.stage.StageFromDb;
import hr.fer.elektrijada.dal.sql.user.SqlUserRepository;
import hr.fer.elektrijada.dal.sql.user.UserFromDb;

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 */
public class BluetoothService {
    // Debugging
    private static final String TAG = "BluetoothChatService";
    private static final boolean D = true;
    // Name for the SDP record when creating server socket
    private static final String NAME = "BluetoothChat";
    // Unique UUID for this application
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    // Member fields
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    private Activity activity;
    /**
     * Constructor. Prepares a new BluetoothActivity session.
     * @param activity  The UI Activity
     * @param handler  A Handler to send messages back to the UI Activity
     */
    public BluetoothService(Activity activity, Handler handler) {
        this.activity = activity;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }
    /**
     * Set the current state of the chat connection
     * @param state  An integer defining the current connection state
     */
    private synchronized void setState(int state) {
       // if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(BluetoothActivity.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }
    /**
     * Return the current connection state. */
    public synchronized int getState() {
        return mState;
    }
    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume() */
    public synchronized void start() {
       // if (D) Log.d(TAG, "start");
        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        // Start the thread to listen on a BluetoothServerSocket
        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }
    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     * @param device  The BluetoothDevice to connect
     */
    public synchronized void connect(BluetoothDevice device) {
        // if (D) Log.d(TAG, "connect to: " + device);
        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }
    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
       // if (D) Log.d(TAG, "connected");
        // Cancel the thread that completed the connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        // Cancel the accept thread because we only want to connect to one device
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}
        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(BluetoothActivity.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothActivity.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        setState(STATE_CONNECTED);
    }
    /**
     * Stop all threads
     */
    public synchronized void stop() {
        //if (D) Log.d(TAG, "stop");
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}
        setState(STATE_NONE);
    }
    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @see ConnectedThread#write()
     */
    public void write() {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write();
    }
    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
        setState(STATE_LISTEN);
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(BluetoothActivity.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothActivity.TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }
    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
        setState(STATE_LISTEN);
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(BluetoothActivity.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(BluetoothActivity.TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }
    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;
        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            // Create a new listening server socket
            try {
                tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "listen() failed", e);
            }
            mmServerSocket = tmp;
        }
        public void run() {
            if (D) Log.d(TAG, "BEGIN mAcceptThread" + this);
            setName("AcceptThread");
            BluetoothSocket socket;
            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed", e);
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    synchronized (BluetoothService.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Situation normal. Start the connected thread.
                            connected(socket, socket.getRemoteDevice());
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                            try {
                                socket.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                        }
                    }
                }
            }
            if (D) Log.i(TAG, "END mAcceptThread");
        }
        public void cancel() {
            if (D) Log.d(TAG, "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of server failed", e);
            }
        }
    }
    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;
        }
        public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");
            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();
            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                connectionFailed();
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }
                // Start the service over to restart listening mode
                BluetoothService.this.start();
                return;
            }
            // Reset the ConnectThread because we're done
            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }
            // Start the connected thread
            connected(mmSocket, mmDevice);
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final ObjectOutputStream oos;
        private final ObjectInputStream ois;
        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;

            ObjectOutputStream tempoos = null;
            ObjectInputStream tempois = null;
            try {
                tempoos = new ObjectOutputStream(mmOutStream);
                tempois = new ObjectInputStream(mmInStream);
            } catch (IOException e) {
                Log.e(TAG, "object i/o streams not created", e);
            }
            oos = tempoos;
            ois = tempois;
        }
        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;
            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
//                    bytes = mmInStream.read(buffer);
//
//                    // Send the obtained bytes to the UI Activity
//                    mHandler.obtainMessage(BluetoothActivity.MESSAGE_READ, bytes, -1, buffer)
//                            .sendToTarget();



                    Object obj;
                    try {
                        obj = ois.readObject();
                    } catch (ClassNotFoundException e) {
                        continue;
                    }

                    Object flag = null;
                    if(obj instanceof List) {
                        List list = (List) obj;
                        if(!list.isEmpty()) {
                            flag = list.get(0);
                        }
                    }

                    if(flag instanceof NewsFromDb) {
                        final SqlNewsRepository newsRepository = new SqlNewsRepository(activity);
                        List<NewsFromDb> all = newsRepository.getAllNews();
                        for(Object newOne : (List)obj) {
                            final NewsFromDb newNews = (NewsFromDb) newOne;
                            int index = all.indexOf(newNews);
                            if (index == -1) {
                                newsRepository.addNewsBluetooth(newNews);
                            } else {
                                final NewsFromDb existing = all.get(index);
                                if (!newNews.detailsSame(existing)) {
                                    askUser(existing, newNews, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SqlUserRepository userRepository = new SqlUserRepository(activity);
                                            SqlNewsRepository newsRepository = new SqlNewsRepository(activity);
                                            userRepository.fixId(newNews.getAuthor());
                                            newNews.setId(existing.getId());
                                            newsRepository.updateNews(newNews);
                                            newsRepository.close();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }
                        newsRepository.close();

                    } else if(flag instanceof UserFromDb) {
                        SqlUserRepository userRepository = new SqlUserRepository(activity);
                        List all = userRepository.getAllUsers();
                        for(Object newOne : (List)obj) {
                            UserFromDb newUser = (UserFromDb) newOne;
                            if (!all.contains(newUser)) {
                                userRepository.createUser(newUser);
                            }
                        }
                        userRepository.close();

                    } else if(flag instanceof FacultyFromDb) {
                        SqlFacultyRepository facultyRepository = new SqlFacultyRepository(activity);
                        List all = facultyRepository.getFaculties();
                        for(Object newOne : (List)obj) {
                            FacultyFromDb newFaculty = (FacultyFromDb) newOne;
                            if (!all.contains(newFaculty)) {
                                facultyRepository.createFaculty(newFaculty);
                            }
                        }
                        facultyRepository.close();

                    } else if(flag instanceof StageFromDb) {
                        SqlStageRepository stageRepository = new SqlStageRepository(activity);
                        List all = stageRepository.getAllStages();
                        for(Object newOne : (List)obj) {
                            StageFromDb newStage = (StageFromDb) newOne;
                            if (!all.contains(newStage)) {
                                stageRepository.createStage(newStage);
                            }
                        }
                        stageRepository.close();

                    }  else if(flag instanceof DuelScoreFromDb) {
                        SqlDuelScoreRepository duelScoreRepository = new SqlDuelScoreRepository(activity);
                        List all = duelScoreRepository.getAllDuelScores();
                        SqlUserRepository users = new SqlUserRepository(activity);
                        SqlDuelRepository duels = new SqlDuelRepository(activity);
                        for(Object newOne : (List)obj) {
                            DuelScoreFromDb newScore = (DuelScoreFromDb) newOne;
                            if (!all.contains(newScore)) {
                                users.fixId(newScore.getUser());
                                duels.fixId(newScore.getDuel());
                                duelScoreRepository.addDuelScore(newScore);
                            }
                        }
                        duels.close();
                        users.close();
                        duelScoreRepository.close();

                    } else if(flag instanceof DuelFromDb) {
                        final SqlDuelRepository duelRepository = new SqlDuelRepository(activity);
                        List<DuelFromDb> all = duelRepository.getAllDuels();
                        SqlCategoryRepository categories = new SqlCategoryRepository(activity);
                        SqlCompetitorRepository competitors = new SqlCompetitorRepository(activity);
                        SqlStageRepository stages = new SqlStageRepository(activity);
                        for(Object newOne : (List)obj) {
                            final DuelFromDb newDuel = (DuelFromDb) newOne;
                            int index = all.indexOf(newDuel);
                            stages.fixId(newDuel.getStage());
                            competitors.fixId(newDuel.getFirstCompetitor());
                            competitors.fixId(newDuel.getSecondCompetitor());
                            categories.fixId(newDuel.getCategory());

                            if (index == -1) {
                                duelRepository.createNewDuel(newDuel);
                            } else {
                                final DuelFromDb existing = all.get(index);
                                if (!newDuel.detailsSame(existing)) {
                                    askUser(existing, newDuel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SqlDuelRepository duels = new SqlDuelRepository(activity);
                                            newDuel.setId(existing.getId());
                                            duels.updateDuel(newDuel);
                                            duels.close();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }
                        categories.close();
                        competitors.close();
                        stages.close();
                        duelRepository.close();

                    } else if(flag instanceof CompetitorFromDb) {
                        SqlCompetitorRepository competitorRepository = new SqlCompetitorRepository(activity);
                        List all = competitorRepository.getAllCompetitors();
                        SqlFacultyRepository faculties = new SqlFacultyRepository(activity);
                        SqlCompetitionRepository competitions = new SqlCompetitionRepository(activity);
                        for(Object newOne : (List)obj) {
                            CompetitorFromDb newCompetitor = (CompetitorFromDb) newOne;
                            if (!all.contains(newCompetitor)) {
                                competitorRepository.fixId(newCompetitor.getGroupCompetitor());
                                faculties.fixId(newCompetitor.getFaculty());
                                competitions.fixId(newCompetitor.getCompetition());
                                competitorRepository.createNewCompetitor(newCompetitor);
                            }
                        }
                        competitions.close();
                        faculties.close();
                        competitorRepository.close();

                    } else if(flag instanceof CompetitionScoreFromDb) {
                        SqlCompetitionScoreRepository competitionScoreRepository = new SqlCompetitionScoreRepository(activity);
                        SqlCompetitionRepository competitionRepository = new SqlCompetitionRepository(activity);
                        SqlUserRepository userRepository = new SqlUserRepository(activity);
                        SqlCompetitorRepository competitorRepository = new SqlCompetitorRepository(activity);
                        List all = competitionScoreRepository.getAllScores();
                        for(Object newOne : (List)obj) {
                            CompetitionScoreFromDb newScore = (CompetitionScoreFromDb) newOne;
                            if (!all.contains(newScore)) {
                                competitionRepository.fixId(newScore.getCompetition());
                                userRepository.fixId(newScore.getUser());
                                competitorRepository.fixId(newScore.getCompetitor());
                                competitionScoreRepository.addScore(newScore);
                            }
                        }
                        competitionRepository.close();
                        competitorRepository.close();
                        userRepository.close();
                        competitionScoreRepository.close();

                    } else if(flag instanceof CompetitionFromDb) {
                        final SqlCompetitionRepository competitionRepository = new SqlCompetitionRepository(activity);
                        List<CompetitionFromDb> all = competitionRepository.getAllCompetitions();
                        SqlCategoryRepository categories = new SqlCategoryRepository(activity);
                        for(Object newOne : (List)obj) {
                            final CompetitionFromDb newCompetition = (CompetitionFromDb) newOne;
                            int index = all.indexOf(newCompetition);

                            categories.fixId(newCompetition.getCategory());

                            if (index == -1) {
                                competitionRepository.createNewCompetition(newCompetition);
                            } else {
                                final CompetitionFromDb existing = all.get(index);
                                if (!newCompetition.detailsSame(existing)) {
                                    askUser(existing, newCompetition, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SqlCompetitionRepository competitions = new SqlCompetitionRepository(activity);
                                            newCompetition.setId(existing.getId());
                                            competitions.updateCompetition(newCompetition);
                                            competitions.close();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }
                        categories.close();
                        competitionRepository.close();

                    } else if(flag instanceof CategoryFromDb) {
                        final SqlCategoryRepository categoryRepository = new SqlCategoryRepository(activity);
                        List<CategoryFromDb> all = categoryRepository.getAllCategories();
                        for(Object newOne : (List)obj) {
                            final CategoryFromDb newCategory = (CategoryFromDb) newOne;
                            int index = all.indexOf(newCategory);

                            if (index == -1) {
                                categoryRepository.createCategory(newCategory);
                            } else {
                                final CategoryFromDb existing = all.get(index);
                                if (!newCategory.detailsSame(existing)) {
                                    askUser(existing, newCategory, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SqlCategoryRepository categoryRepository = new SqlCategoryRepository(activity);
                                            newCategory.setId(existing.getId());
                                            categoryRepository.updateCategory(newCategory);
                                            categoryRepository.close();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }
                        categoryRepository.close();
                    }

                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }
            }
        }
        /**
         * Write to the connected OutStream.
         */
        public void write() {
            try {
//                mmOutStream.write(buffer);
//                // Share the sent message back to the UI Activity
//                mHandler.obtainMessage(BluetoothActivity.MESSAGE_WRITE, -1, -1, buffer)
//                        .sendToTarget();

                //PAZI NA POREDAK KOJIM SE SALJE!!!!

                SqlCategoryRepository categoryRepository = new SqlCategoryRepository(activity);
                oos.writeObject(categoryRepository.getAllCategories());
                categoryRepository.close();
//                for(Serializable category : allCategories) {
//                    oos.writeObject(category);
//                }

                SqlFacultyRepository facultyRepository = new SqlFacultyRepository(activity);
                oos.writeObject(facultyRepository.getFaculties());
                facultyRepository.close();
//                for(Serializable faculty : allFaculties) {
//                    oos.writeObject(faculty);
//                }

                SqlUserRepository userRepository = new SqlUserRepository(activity);
                oos.writeObject(userRepository.getAllUsers());
                userRepository.close();
//                for (Serializable user : allUsers) {
//                    oos.writeObject(user);
//                }

                SqlNewsRepository newsRepository = new SqlNewsRepository(activity);
                oos.writeObject(newsRepository.getAllNews());
                newsRepository.close();
//                for (Serializable news : allNews) {
//                    oos.writeObject(news);
//                }


                SqlCompetitionRepository competitionRepository = new SqlCompetitionRepository(activity);
                oos.writeObject(competitionRepository.getAllCompetitions());
                competitionRepository.close();
//                for(Serializable competition : allCompetitions) {
//                    oos.writeObject(competition);
//                }

                SqlStageRepository stageRepository = new SqlStageRepository(activity);
                oos.writeObject(stageRepository.getAllStages());
                stageRepository.close();
//                for(Serializable stage : allStages) {
//                    oos.writeObject(stage);
//                }

                { // koristim blok da "zaboravi" referencu na listu, jer je na mom mobu bio prespor s garbage collectorom
                    SqlCompetitorRepository competitorRepository = new SqlCompetitorRepository(activity);
                    List<CompetitorFromDb> allCompetitors = competitorRepository.getAllCompetitors();
                    competitorRepository.close();
                    //sortiram tako da prvo pošalje timove, jer da bi dodao pojedinca koji je u grupi grupa već mora biti u bazi
                    Collections.sort(allCompetitors, new Comparator<CompetitorFromDb>() {
                        @Override
                        public int compare(CompetitorFromDb lhs, CompetitorFromDb rhs) {
                            if (lhs.getGroupCompetitor() == null) return -1;
                            else return 1;
                        }
                    });
                    oos.writeObject(allCompetitors);
                }
//                for(Serializable competitor : allCompetitors) {
//                    oos.writeObject(competitor);
//                }

                SqlCompetitionScoreRepository competitionScoreRepository = new SqlCompetitionScoreRepository(activity);
                oos.writeObject(competitionScoreRepository.getAllScores());
                competitionScoreRepository.close();
//                for(Serializable competitionScore : allCompetitionScores) {
//                    oos.writeObject(competitionScore);
//                }

                SqlDuelRepository duelRepository = new SqlDuelRepository(activity);
                oos.writeObject(duelRepository.getAllDuels());
                duelRepository.close();
//                for(Serializable duel : allDuels) {
//                    oos.writeObject(duel);
//                }

                SqlDuelScoreRepository duelScoreRepository = new SqlDuelScoreRepository(activity);
                oos.writeObject(duelScoreRepository.getAllDuelScores());
                duelScoreRepository.close();
//                for(Serializable duelScore : allDuelScores) {
//                    oos.writeObject(duelScore);
//                }

                //mHandler.obtainMessage(BluetoothActivity.CHANGE_OBJECT, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private void askUser(final IDetails my, final IDetails others, final DialogInterface.OnClickListener listener) {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                        alertDialog.setTitle("Konflikt");
                        alertDialog.setMessage(my.info()+"\n\nMoji detalji:\n"+my.details()+"\n\nTuđi detalji:\n"+others.details());
                        alertDialog.setPositiveButton("Preuzmi tuđe", listener);
                        alertDialog.setNegativeButton("Ostavi moje",  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }
        );
    }
}