package com.example.controller;

import com.example.dto.PageDTO;
import com.example.dto.ResponseDTO;
import com.example.dto.ScoreDTO;
import com.example.dto.SearchScoreDTO;
import com.example.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;

    @PostMapping("/")
    public ResponseDTO<Void> newScore(@RequestBody @Valid ScoreDTO scoreDTO){
        scoreService.create(scoreDTO);
        return ResponseDTO.<Void>builder().msg("Success").status(200).build();
    }

    @DeleteMapping("/")
    public ResponseDTO<Void> deleteScore(@RequestParam("id") int id){
        scoreService.delete(id);
        return ResponseDTO.<Void>builder().msg("Success").status(200).build();
    }
    @PutMapping("/")
    public ResponseDTO<ScoreDTO> updateScore(@RequestBody @Valid ScoreDTO scoreDTO){
        scoreService.update(scoreDTO);
        return ResponseDTO.<ScoreDTO>builder().data(scoreDTO).msg("Success").status(200).build();
    }

    @GetMapping("/list")
    public ResponseDTO<List<ScoreDTO>> getAll(){
        return ResponseDTO.<List<ScoreDTO>>builder().data(scoreService.getAll()).status(200).msg("Success").build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<List<ScoreDTO>>> search(@RequestBody SearchScoreDTO searchScoreDTO){
        return ResponseDTO.<PageDTO<List<ScoreDTO>>>builder().msg("Success").status(200).data(scoreService.search(searchScoreDTO)).build();
    }
}
