package com.shumin.study;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.TimeUtils;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.shumin.study.bean.Question;
import com.shumin.study.bean.Questions;
import com.shumin.study.bean.UserInfo;
import com.shumin.study.database.OrmDBUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Guchuan on 2016/5/13.
 */
public class Utility {

    public static final String LOGGED_USER = "logged_user";
    public static final String INSERT_DEFAULT_QUESTIONS = "default_questions";

    public static void setStringPref(Context context, String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPref(Context context, String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, null);
    }

    public static void setBooleanPref(Context context, String key, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBooleanPref(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

    public static void insertDefaultAccount(OrmLiteSqliteOpenHelper helper) {
        try {
            UserInfo userInfo = OrmDBUtils.queryUserInfoByUsername(helper, Constants.DEFAULT_MANAGER_USERNAME);
            if (userInfo == null) {
                userInfo = new UserInfo();
                userInfo.setManager(true);
                userInfo.setUserName(Constants.DEFAULT_MANAGER_USERNAME);
                userInfo.setPassword(Constants.DEFAULT_MANAGER_PASSWORD);
                OrmDBUtils.createOrUpdateUserInfo(helper, userInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertDefaultQuestions(Context context, OrmLiteSqliteOpenHelper helper) {
        String name = "综合小测1";
        Questions questions = null;
        try {
            OrmDBUtils.queryQuestionsByName(helper, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (questions != null){
            return;
        }
        questions = new Questions();
        questions.setName(name);
        questions.setDescription("入门");
        questions.setExamCount(0);
        questions.setCreateTime(getDate());
        questions.setLevel(1);
        OrmDBUtils.createOrUpdateQuestions(helper, questions);

        Questions q = null;
        try {
            q = OrmDBUtils.queryQuestionsByName(helper, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (q == null) {
            return;
        }
        long id = q.getId();

        // 1
        Question question1 = new Question();
        question1.setSubject("Http协议默认使用（ ）端口。");
        question1.setOption1("80");
        question1.setOption2("139");
        question1.setOption3("443");
        question1.setOption4("1");
        question1.setType(Question.TYPE_CHOICE);
        question1.setRightAnswer(3);
        question1.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question1);

        // 2
        Question question2 = new Question();
        question2.setSubject("Http是（）协议");
        question2.setOption1("物理层");
        question2.setOption2("应用层");
        question2.setOption3("传输层");
        question2.setOption4("网络接口层");
        question2.setType(Question.TYPE_CHOICE);
        question2.setRightAnswer(1);
        question2.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question2);

        // 3
        Question question3 = new Question();
        question3.setSubject("关于IP协议的选择题");
        question3.setOption1("仅靠IP协议，计算机就能在因特网进行通信");
        question3.setOption2("IP协议提供的是一种可靠的传输");
        question3.setOption3("IP协议就是传输控制协议");
        question3.setOption4("IP协议是一种面向无线连接的协议");
        question3.setRightAnswer(1);
        question3.setType(Question.TYPE_CHOICE);
        question3.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question3);

        // 4
        Question question4 = new Question();
        question4.setSubject("从一个C类网络的主机地址借3位时，可建立多少个可用子网");
        question4.setOption1("3");
        question4.setOption2("6");
        question4.setOption3("8");
        question4.setOption4("12");
        question4.setRightAnswer(1);
        question4.setType(Question.TYPE_CHOICE);
        question4.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question4);

        // 5
        Question question5 = new Question();
        question5.setSubject("下面哪个是有效的B类地址");
        question5.setOption1("15.129.89.76");
        question5.setOption2("151.129.89.76");
        question5.setOption3("193.129.89.76");
        question5.setOption4("223.129.89.76");
        question5.setRightAnswer(1);
        question5.setType(Question.TYPE_CHOICE);
        question5.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question5);

        // 6
        Question question6 = new Question();
        question6.setSubject("一个IP数据报的最大长度是多少个字节");
        question6.setOption1("521");
        question6.setOption2("576");
        question6.setOption3("1500");
        question6.setOption4("65535");
        question6.setRightAnswer(3);
        question6.setType(Question.TYPE_CHOICE);
        question6.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question6);

        // 7
        Question question7 = new Question();
        question7.setSubject("IP报头的最大长度是多少个字节");
        question7.setOption1("20");
        question7.setOption2("60");
        question7.setOption3("64");
        question7.setOption4("256");
        question7.setRightAnswer(1);
        question7.setType(Question.TYPE_CHOICE);
        question7.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question7);

        // 8
        Question question8 = new Question();
        question8.setSubject("下列对于IP地址的描述不正确的是");
        question8.setOption1("主机部分全为“1”的IP址址称为有限广播");
        question8.setOption2("0.x.y.z表示本网络的指定主机");
        question8.setOption3("一个A类网的IP址址x.0.0.0表示x这个网络");
        question8.setOption4("IP地址172.16.0.0~172.31.255.255属于保留地址");
        question8.setRightAnswer(0);
        question8.setType(Question.TYPE_CHOICE);
        question8.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question8);

        // 9
        Question question9 = new Question();
        question9.setSubject("TCP进程如何处理失败的连接");
        question9.setOption1("发送一个FIN段询问目的端的状态");
        question9.setOption2("在超出最大重试次数后发送一个复位（RST）段");
        question9.setOption3("发送一个RST段重置目的端的重传计时器");
        question9.setOption4("发送一个ACK段，立即终止该连接");
        question9.setRightAnswer(1);
        question9.setType(Question.TYPE_CHOICE);
        question9.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question9);

        // 10
        Question question10 = new Question();
        question10.setSubject("下列哪项有关UDP的描述是正确的");
        question10.setOption1("UDP是一种面向连接的协议，用于在网络应用程序间建立虚拟线路");
        question10.setOption2("UDP为IP网络中的可靠通信提供错误检测和故障恢复功能");
        question10.setOption3("文件传输协议FTP就是基本UDP协议来工作的");
        question10.setOption4("UDP服务器必须在约定端口收听服务请求，否则该事务可能失败");
        question10.setRightAnswer(3);
        question10.setType(Question.TYPE_CHOICE);
        question10.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question10);

        // 11
        Question question11 = new Question();
        question11.setSubject("发送应用程序可以通过设置下列哪两个标志来使TCP进程在传送缓冲器填满前发送数据");
        question11.setOption1("FIL和PSH");
        question11.setOption2("PSH和URG");
        question11.setOption3("UGR和FIN");
        question11.setOption4("FIL和FIN");
        question11.setRightAnswer(1);
        question11.setType(Question.TYPE_CHOICE);
        question11.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question11);

        // 12
        Question question12 = new Question();
        question12.setSubject("Telnet是TCP/IP哪一层的协议");
        question12.setOption1("网络接口层");
        question12.setOption2("网际层");
        question12.setOption3("传输层");
        question12.setOption4("应用层");
        question12.setRightAnswer(3);
        question12.setType(Question.TYPE_CHOICE);
        question12.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question12);

        // 13
        Question question13 = new Question();
        question13.setSubject("C类网络地址共有多少个网络位和主机位");
        question13.setOption1("6个网络位，16个主机位");
        question13.setOption2("8个网络位，24个主机位");
        question13.setOption3("24个网络位，8个主机位");
        question13.setOption4("30个网络位，2个主机位");
        question13.setRightAnswer(2);
        question13.setType(Question.TYPE_CHOICE);
        question13.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question13);

        // 14
        Question question14 = new Question();
        question14.setSubject("下列哪个设备可支持在独立的IP网络之间通信");
        question14.setOption1("集线器");
        question14.setOption2("网桥");
        question14.setOption3("第2层交换机");
        question14.setOption4("路由器");
        question14.setRightAnswer(3);
        question14.setType(Question.TYPE_CHOICE);
        question14.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question14);

        // 15
        Question question15 = new Question();
        question15.setSubject("对网际控制协议（ICMP）描述错误的是");
        question15.setOption1("ICMP封装在IP数据报的数据部分");
        question15.setOption2("ICMP消息的传输是可靠的");
        question15.setOption3("一般不把ICMP作为高层协议，而只作为IP必需的一个部分。");
        question15.setOption4("ICMP一般用于在Internet上进行差错报告");
        question15.setRightAnswer(1);
        question15.setType(Question.TYPE_CHOICE);
        question15.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question15);

        insertDefaultQuestions2(context, helper);
    }

    private static void insertDefaultQuestions2(Context context, OrmLiteSqliteOpenHelper helper) {
        String name = "综合小测2";
        Questions questions = null;
        try {
            OrmDBUtils.queryQuestionsByName(helper, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (questions != null){
            return;
        }
        questions = new Questions();
        questions.setName(name);
        questions.setDescription("进阶");
        questions.setExamCount(0);
        questions.setCreateTime(getDate());
        questions.setLevel(2);
        OrmDBUtils.createOrUpdateQuestions(helper, questions);

        Questions q = null;
        try {
            q = OrmDBUtils.queryQuestionsByName(helper, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (q == null) {
            return;
        }
        long id = q.getId();

        // 1
        Question question1 = new Question();
        question1.setSubject("TCP/IP模型的网络接口层对应于OSI模型的");
        question1.setOption1("物理层和数据链路层");
        question1.setOption2("数据链路层和网络层");
        question1.setOption3("物理层、数据链路层和网络层");
        question1.setOption4("仅网络层");
        question1.setRightAnswer(0);
        question1.setType(Question.TYPE_CHOICE);
        question1.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question1);

        // 1
        Question question2 = new Question();
        question2.setSubject("IP报头的最大长度是多少个字节");
        question2.setOption1("20");
        question2.setOption2("60");
        question2.setOption3("64");
        question2.setOption4("256");
        question2.setRightAnswer(1);
        question2.setType(Question.TYPE_CHOICE);
        question2.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question2);

        // 3
        Question question3 = new Question();
        question3.setSubject("下列哪个协议可提供“ping”和“traceroute”这样的故障诊断功能");
        question3.setOption1("ICMP      ");
        question3.setOption2("IGMP      ");
        question3.setOption3("ARP      ");
        question3.setOption4("RARP");
        question3.setRightAnswer(0);
        question3.setType(Question.TYPE_CHOICE);
        question3.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question3);

        // 4
        Question question4 = new Question();
        question4.setSubject("TCP在应用程序之间建立了下列哪种类型的线路");
        question4.setOption1("虚拟线路");
        question4.setOption2("动态线路");
        question4.setOption3("物理线路");
        question4.setOption4("无连接线路");
        question4.setRightAnswer(0);
        question4.setType(Question.TYPE_CHOICE);
        question4.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question4);

        // 5
        Question question5 = new Question();
        question5.setSubject("在发送TCP接收到确认ACK之前，由其设置的重传计时器到时，这时发送TCP会");
        question5.setOption1("重传重要的数据段");
        question5.setOption2("放弃该连接");
        question5.setOption3("调整传送窗口尺寸");
        question5.setOption4("向另一个目标端口重传数据");
        question5.setRightAnswer(0);
        question5.setType(Question.TYPE_CHOICE);
        question5.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question5);

        // 6
        Question question6 = new Question();
        question6.setSubject("IP负责数据交互的可靠性");
        question6.setOption1("真");
        question6.setOption2("假");
        question6.setRightAnswer(1);
        question6.setType(Question.TYPE_CHOICE);
        question6.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question6);

        // 7
        Question question7 = new Question();
        question7.setSubject("对于ICMP协议，以下描述正确的是");
        question7.setOption1("ICMP只能用于传输差错报文。");
        question7.setOption2("ICMP只能用于传输控制报文。");
        question7.setOption3("ICMP既不能传输差错报文，也不能传输控制报文。");
        question7.setOption4("ICMP不仅用于传输差错报文，而且用于传输控制报文。");
        question7.setRightAnswer(3);
        question7.setType(Question.TYPE_CHOICE);
        question7.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question7);

        // 8
        Question question8 = new Question();
        question8.setSubject("若某IP地址的网络号全部为0，则该IP地址表示");
        question8.setOption1("本网络");
        question8.setOption2("直接广播地址");
        question8.setOption3("有限广播地址");
        question8.setOption4("回送地址");
        question8.setRightAnswer(1);
        question8.setType(Question.TYPE_CHOICE);
        question8.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question8);

        // 9
        Question question9 = new Question();
        question9.setSubject("最早出现和使用的计算机网络是");
        question9.setOption1("Aarpanet     ");
        question9.setOption2("Ethernet      ");
        question9.setOption3("Bitnet       ");
        question9.setOption4("Internet");
        question9.setRightAnswer(0);
        question9.setType(Question.TYPE_CHOICE);
        question9.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question9);

        // 10
        Question question10 = new Question();
        question10.setSubject("Internet上的WWW服务在哪一种协议直接支持下实现的");
        question10.setOption1("TCP    ");
        question10.setOption2("IP   ");
        question10.setOption3("HTTP    ");
        question10.setOption4("SMTP");
        question10.setRightAnswer(2);
        question10.setType(Question.TYPE_CHOICE);
        question7.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question10);

        // 11
        Question question11 = new Question();
        question11.setSubject("在B类网中，IP地址用几位表示主机地址");
        question11.setOption1("24位");
        question11.setOption2("16位");
        question11.setOption3("8位 ");
        question11.setOption4("4位");
        question11.setRightAnswer(2);
        question11.setType(Question.TYPE_CHOICE);
        question11.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question11);

        // 12
        Question question12 = new Question();
        question12.setSubject("在IP网络中，子网掩码的作用是");
        question12.setOption1("掩盖IP地址");
        question12.setOption2("掩盖TCP端口号");
        question12.setOption3("获得IP地址中的主机号");
        question12.setOption4("获取IP地址中的网络号");
        question12.setRightAnswer(3);
        question12.setType(Question.TYPE_CHOICE);
        question12.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question12);

        // 13
        Question question13 = new Question();
        question13.setSubject("对网际控制协议（ICMP）描述错误的是");
        question13.setOption1("ICMP封装在IP数据报的数据部分");
        question13.setOption2("ICMP消息的传输是可靠的");
        question13.setOption3("一般不把ICMP作为高层协议，而只作为IP必需的一个部分。");
        question13.setOption4("ICMP一般用于在Internet上进行差错报告");
        question13.setRightAnswer(3);
        question13.setType(Question.TYPE_CHOICE);
        question13.setQuestionsId(id);
        OrmDBUtils.createOrUpdateQuestion(helper, question13);

        Utility.setBooleanPref(context, INSERT_DEFAULT_QUESTIONS, true);
    }

    public static String getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format.format(calendar.getTime());
    }

    public static String getTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format.format(calendar.getTime());
    }
}
