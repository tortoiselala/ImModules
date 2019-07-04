package com.tortoiselala.entity;

/**
 * @author tortoiselala
 */
public class AccountLog {

// --Commented out by Inspection START (2019-07-01 22:33):
// --Commented out by Inspection START (2019-07-01 22:33):
////    /**
// --Commented out by Inspection START (2019-07-01 22:33):
//////     * 用户账户操作日志主键
//////     */
//////    private long id;
// --Commented out by Inspection STOP (2019-07-01 22:33)
//// --Commented out by Inspection STOP (2019-07-01 22:33)
// --Commented out by Inspection START (2019-07-01 22:33):
//// --Commented out by Inspection STOP (2019-07-01 22:33)
//
//    /**
// --Commented out by Inspection STOP (2019-07-01 22:33)
// --Commented out by Inspection START (2019-07-01 22:33):
//     * 日志名称(login,register,logout)
//     */
//    private String logName;
// --Commented out by Inspection STOP (2019-07-01 22:33)

    /**
     * 用户id
     */
    private String uid;

// --Commented out by Inspection START (2019-07-01 22:33):
// --Commented out by Inspection START (2019-07-01 22:33):
////    /**
////     * 创建时间
////     */
////    private long createTime;
// --Commented out by Inspection STOP (2019-07-01 22:33)
// --Commented out by Inspection STOP (2019-07-01 22:33)

    /**
     * 是否执行成功(0失败1成功)
     */
    private int succeed;

    /**
     * 具体消息
     */
    private String message;

    /**
     * 登录ip
     */
    private String ip;
}
