package com.example.service;

import com.example.dto.*;
import com.example.entity.Score;
import com.example.repository.ScoreRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    @Autowired
    ScoreRepo scoreRepo;

    public ScoreDTO convert(Score score){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(score, ScoreDTO.class);
    }
    @Transactional
    public void create(ScoreDTO scoreDTO){
        scoreRepo.save(new ModelMapper().map(scoreDTO, Score.class));
    }

    @Transactional
    public void delete(int id){
        scoreRepo.deleteById(id);
    }

    @Transactional
    public void update(ScoreDTO scoreDTO){
        scoreRepo.save(new ModelMapper().map(scoreDTO, Score.class));
    }


    public List<ScoreDTO> getAll(){
        return scoreRepo.findAll().stream().map(u->convert(u)).collect(Collectors.toList());
    }

    public PageDTO<List<ScoreDTO>> search(SearchScoreDTO searchScoreDTO){
        Sort sortBy=Sort.by("id").ascending(); //sap xep theo ten va tuoi (mac dinh)

        //sort theo yeu cau
        if(StringUtils.hasText(searchScoreDTO.getSortedField())){ //check xem co empty khong
            sortBy=Sort.by(searchScoreDTO.getSortedField());
        }
        if(searchScoreDTO.getCurrentPage()==null){
            searchScoreDTO.setCurrentPage(0);
        }
        if(searchScoreDTO.getSize()==null){
            searchScoreDTO.setSize(20);
        }

        //tao PageRequest de truyen vao Pageable
        PageRequest pageRequest = PageRequest.of(searchScoreDTO.getCurrentPage(),searchScoreDTO.getSize(),sortBy);
        Page<Score> page = scoreRepo.findAll(pageRequest);
        if(StringUtils.hasText(searchScoreDTO.getKeyword())&&searchScoreDTO.getCourseId()==null&&searchScoreDTO.getStudentId()==null){
            page = scoreRepo.findByStudentCodeOrCourseName("%"+searchScoreDTO.getKeyword()+"%", pageRequest);
        }else if(!StringUtils.hasText(searchScoreDTO.getKeyword())&&searchScoreDTO.getCourseId()!=null&&searchScoreDTO.getStudentId()==null){
            page = scoreRepo.searchByCourse(searchScoreDTO.getCourseId(), pageRequest);
        }else if(!StringUtils.hasText(searchScoreDTO.getKeyword())&&searchScoreDTO.getCourseId()==null&&searchScoreDTO.getStudentId()!=null){
            page = scoreRepo.searchByStudent(searchScoreDTO.getStudentId(), pageRequest);

        }else if(!StringUtils.hasText(searchScoreDTO.getKeyword())&&searchScoreDTO.getCourseId()!=null&&searchScoreDTO.getStudentId()!=null){
            page = scoreRepo.findByStudentAndCourse(searchScoreDTO.getStudentId(), searchScoreDTO.getCourseId(), pageRequest);
        }else if(StringUtils.hasText(searchScoreDTO.getKeyword())&&searchScoreDTO.getCourseId()!=null&&searchScoreDTO.getStudentId()==null){
            page = scoreRepo.findByKeywordAndCourseId(searchScoreDTO.getCourseId(),"%"+searchScoreDTO.getKeyword()+"%", pageRequest);
        }else if(StringUtils.hasText(searchScoreDTO.getKeyword())&&searchScoreDTO.getCourseId()==null&&searchScoreDTO.getStudentId()!=null){
            page = scoreRepo.findByKeywordAndStudentId(searchScoreDTO.getStudentId(),"%"+searchScoreDTO.getKeyword()+"%", pageRequest);
        }else{
            page = scoreRepo.findByStudentAndCourse(searchScoreDTO.getStudentId(), searchScoreDTO.getCourseId(), pageRequest);
        }

        PageDTO<List<ScoreDTO>> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setSize(page.getSize());
        //List<User> users = page.getContent();
        List<ScoreDTO> scoreDTOS = page.get().map(u->convert(u)).collect(Collectors.toList());

        //T: List<UserDTO>
        pageDTO.setData(scoreDTOS);
        return pageDTO;
    }
}
