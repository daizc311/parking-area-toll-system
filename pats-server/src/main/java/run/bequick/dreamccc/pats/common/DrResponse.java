package run.bequick.dreamccc.pats.common;

import lombok.Data;


@Data
public class DrResponse<DATA> {

    private String message;

    private DATA data;

    private Integer status;


    public static <T> DrResponse<T> data(T data) {
        DrResponse<T> success = success();
        success.setData(data);
        return success;
    }

    public static <T> DrResponse<T> success() {
        var response = new DrResponse<T>();
        response.setStatus(0);
        response.setMessage("成功");
        return response;
    }

    public static <D> DrResponse<D> failed() {

        return failed("失败");
    }

    public static <D> DrResponse<D> failed(String message) {
        DrResponse<D> response = new DrResponse<>();
        response.setStatus(1);
        response.message = message;
        return response;
    }

}
