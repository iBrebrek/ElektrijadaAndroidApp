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
 * Created by Ivica Brebrek
 */
public class AddStudent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_student);

        SqlFacultyRepository repo = new SqlFacultyRepository(this);
        final List<FacultyFromDb> faculties = repo.getFaculties();
        repo.close();
        ArrayList<String> names = new ArrayList<>();
        for(FacultyFromDb faculty : faculties) {
            names.add(faculty.getName());
        }

        Spinner dropdown = (Spinner) findViewById(R.id.add_student_faculties);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names);
        dropdown.setAdapter(adapter);

        findViewById(R.id.add_student_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacultyFromDb faculty = faculties.get((int)((Spinner) findViewById(R.id.add_student_faculties)).getSelectedItemId());

                String name = ((EditText) findViewById(R.id.add_student_first_name)).getText().toString().trim();
                String surename = ((EditText) findViewById(R.id.add_student_surename)).getText().toString().trim();
                if(name.isEmpty() || surename.isEmpty()) {
                    Toast.makeText(AddStudent.this, "Student mora imati ime i prezime", Toast.LENGTH_SHORT).show();
                    return;
                }
                SqlCompetitorRepository competitorRepository = new SqlCompetitorRepository(AddStudent.this);
                competitorRepository.createNewCompetitor(new CompetitorFromDb(
                        -1,
                        name,
                        surename,
                        true,
                        null,
                        faculty,
                        null,
                        -1,
                        false
                ));
                competitorRepository.close();
                AddStudent.this.finish();
            }
        });
    }
}
