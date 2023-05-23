package com.example.repository;


import com.example.dto.AvgScoreByCourse;
import com.example.entity.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface ScoreRepo extends JpaRepository<Score, Integer> {
    @Query("SELECT s FROM Score s where s.student.userId = :sid")
    Page<Score> searchByStudent(@Param("sid")int studentId, Pageable pageable);

    @Query("SELECT s from Score s where s.course.id= :cid")
    Page<Score> searchByCourse(@Param("cid") int courseId, Pageable pageable);

    //trung binh cong
    @Query("SELECT new com.example.dto.AvgScoreByCourse(s.course.id,s.course.name, AVG(s.score)) from Score s  GROUP BY s.course.id")
    Page<AvgScoreByCourse> avgScoreByCourse(Pageable pageable);



    @Query("SELECT s from Score s where s.course.name like :x or s.student.studentCode like :x")
    Page<Score>findByStudentCodeOrCourseName(@Param("x") String keyword, Pageable pageable);

    Page<Score>findAll(Pageable pageable);

    @Query("SELECT s from Score s where s.student.userId=:sid and s.course.id=:cid")
    Page<Score>findByStudentAndCourse(@Param("sid") int student_id, @Param("cid") int course_id, Pageable pageable);

    @Query("SELECT s from Score s where s.course.id=:cid and (s.course.name like :x or s.student.studentCode like :x)")
    Page<Score> findByKeywordAndCourseId(@Param("cid") int course_id, @Param("x") String keyword, Pageable pageable);

    @Query("SELECT s from Score s where s.student.userId=:sid and (s.course.name like :x or s.student.studentCode like :x)")
    Page<Score> findByKeywordAndStudentId(@Param("sid") int student_id, @Param("x") String keyword, Pageable pageable);
}
