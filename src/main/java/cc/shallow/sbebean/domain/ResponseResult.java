package cc.shallow.sbebean.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应结果
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseResult {
    private int status;
    private Object result;
    private String message;

    public static ResponseResult ok(Object result){
        return new ResponseResult(0, result, "操作成功");
    }

    public static ResponseResult ok(){
        return new ResponseResult(0, "", "操作成功");
    }

    public static ResponseResult ok(Object result, String message){
        return new ResponseResult(0, result, message);
    }

    public static ResponseResult fail(String message){
        return new ResponseResult(1, null, message);
    }
}
