package run.bequick.dreamccc.pats.common;

import lombok.Data;


@Data
public class DrResponse<DATA> {

    private String message;

    private DATA data;

    private Integer status;



    public static <T> DrResponse<T> data(T data){
        var response = new DrResponse<T>();
        response.setData(data);
        response.setStatus(0);
        response.setMessage("成功");
        return response;
    }
    public static <T> DrResponse<T> success(){
        var response = new DrResponse<T>();
        response.setStatus(0);
        response.setMessage("成功");
        return response;
    }
}
