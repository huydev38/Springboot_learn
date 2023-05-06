package com.example.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//đây là các hàm của lombok, dùng builder để tạo đối tượng mà không cần dùng new(...) (google thêm)
public class ResponseDTO<T> {
    private int status; //200, 400, 500
    private String msg; //

    //nếu = null thì không trả về
   @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

   //constructor tuy chinhd
   public ResponseDTO(int status, String msg){
       super();
       this.status=status;
       this.msg=msg;
   }
}
