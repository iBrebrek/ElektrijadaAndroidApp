package hr.fer.elektrijada.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.fer.elektrijada.R;
import hr.fer.elektrijada.dal.sql.competitor.CompetitorFromDb;
import hr.fer.elektrijada.dal.sql.competitor.SqlCompetitorRepository;
import hr.fer.elektrijada.dal.sql.faculty.FacultyFromDb;
import hr.fer.elektrijada.dal.sql.faculty.SqlFacultyRepository;

/**
 * Created by Ivica Brebrek on 27.5.2016..
 */
public class AddFaculty extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_faculty);

        SqlFacultyRepository repo = new SqlFacultyRepository(this);
        final List<FacultyFromDb> faculties = repo.getFaculties();
        repo.close();
        final ArrayList<String> names = new ArrayList<>();
        for(FacultyFromDb faculty : faculties) {
            names.add(faculty.getName());
        }
        names.add(0, "Novi fakultet: ");

        Spinner dropdown = (Spinner) findViewById(R.id.select_faculty);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        dropdown.setAdapter(adapter);

        findViewById(R.id.add_faculty_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.add_faculty_name)).getText().toString().trim();
                if(name.isEmpty()) {
                    Toast.makeText(AddFaculty.this, "Ime ne može biti prazno", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(names.contains(name)) {
                    Toast.makeText(AddFaculty.this, name+" već postoji", Toast.LENGTH_SHORT).show();
                    return;
                }

                SqlFacultyRepository facultyRepository = new SqlFacultyRepository(AddFaculty.this);

                int index = (int)((Spinner) findViewById(R.id.select_faculty)).getSelectedItemId();
                if(index == 0) {
                    long id = facultyRepository.createFaculty(new FacultyFromDb(-1, name, null));
                    SqlCompetitorRepository competitorRepository = new SqlCompetitorRepository(AddFaculty.this);
                    competitorRepository.createNewCompetitor(new CompetitorFromDb(
                            -1,
                            name,
                            null,
                            false,
                            null,
                            facultyRepository.getFaculty((int)id),
                            null,
                            -1,
                            false
                    ));
                    competitorRepository.close();
                } else {
                    FacultyFromDb faculty = faculties.get(index - 1);
                    faculty.setName(name);
                    facultyRepository.updateFaculty(faculty);
                }
                facultyRepository.close();
                AddFaculty.this.finish();
            }
        });
    }
}
