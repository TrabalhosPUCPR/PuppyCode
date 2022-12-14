package com.bradesco.projetoprogramacao.controller;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.bradesco.projetoprogramacao.model.course.Course;
import com.bradesco.projetoprogramacao.R;
import com.bradesco.projetoprogramacao.model.services.localServices.CourseService;
import com.bradesco.projetoprogramacao.view.CoursesListAdapter;

import java.util.Objects;

public class CoursesList extends AppCompatActivity {

    RecyclerView rcvCourses;
    CoursesListAdapter coursesAdapter;
    protected static ActivityResultLauncher<Intent> resultOpenCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Courses");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);

        resultOpenCourse = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK) {
                int id = result.getData().getIntExtra("id", -1);
                CourseService service = new CourseService(this);
                Course course = service.get(id);
                service.close();
                course.setCompleted(true);
                service.edit(course, course.getId());
                int index = result.getData().getIntExtra("position", -1);
                coursesAdapter.updateCourseList(service.getList(), index);
                coursesAdapter.notifyItemChanged(index);
            }
        });
        createRcvView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return false;
    }

    void createRcvView(){
        rcvCourses = findViewById(R.id.rcv_courses);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rcvCourses.setLayoutManager(llm);
        CourseService service = new CourseService(this);
        coursesAdapter = new CoursesListAdapter(this, resultOpenCourse, service.getList());
        service.close();
        rcvCourses.setAdapter(coursesAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}