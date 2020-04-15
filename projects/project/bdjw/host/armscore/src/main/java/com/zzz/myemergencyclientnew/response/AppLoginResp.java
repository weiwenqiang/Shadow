package com.zzz.myemergencyclientnew.response;

public class AppLoginResp {
    /**
     * serveList : [{title='公告', onClick=7, route='/ServeNotice/fragment', netApi='/app/apply/emergKnowl'}, {title='领导', onClick=9, route='/BossLead/fragment', netApi='001100000002'}, {title='生活', onClick=8, route='/WebSingle/fragment', netApi='http://vue.bxapi.sxbdjw.com/#/notice'}, {title='客服', onClick=8, route='/WebSingle/fragment', netApi='https://yzf.qq.com/xv/web/static/chat/index.html?sign=37ef9b97db7756c02b4c99e84fe5b761ba3f90a071c11e624a1357b286688a29f9cf41243f42d9fd2685ec340fc0895a2d92ea'}]
     * applyList : [{title='权威栏目', onClick=7, route='/ProgramaList/fragment', netApi='/app/apply/emergKnowl'}, {title='应急知识', onClick=4, route='/TangramSingle/fragment', netApi='/app/apply/emergKnowl'}, {title='学生军训', onClick=4, route='/TangramSingle/fragment', netApi='/app/apply/studentMilitary'}, {title='民兵预备役', onClick=4, route='/TangramSingle/fragment', netApi='/app/apply/militiaReserve'}, {title='国防教育', onClick=4, route='/TangramSingle/fragment', netApi='/app/apply/defenceEdu'}, {title='创业创收', onClick=4, route='/TangramSingle/fragment', netApi='/app/apply/careerEarn'}, {title='政治教育', onClick=2, route='/WebSingle/fragment', netApi='http://edu.bxapi.sxbdjw.com/web/index.html'}, {title='实用技术', onClick=2, route='/WebSingle/fragment', netApi='http://www.cnncty.com/mobile/'}]
     * userinfo : {"status_mbvalidation":"0","mbid":"","code":"200","uname":"13621123057","rongyun_token":"u+9l6qg4pM/YN6Oj4uqoQqI0WQFcueaggJ4WbBYsYEuYx9PrV3O411o/DhQ7llhXz15Sea0Lc6EsO30xZ3MDhaWB2FGcSakBVHwQ5aKLdc4=","photo":"/group1/M00/DF/91/wKg8Fl14pNeACbgtAALp47J5UC0278.jpg","xinge_token":"376be5d284574e7a5cf4be39f8e46c2bb5a5a9bb","uid":"3411605077289336832","company_authen_status":"1","subsystem_id":"100000000","is_boss":"false","is_charge":"0","idcard":"140109199301272515","status_authentication":"1","nickname":"哈喽","name":"魏文强","rongyun_is_open":"0","status_jgvalidation":"0"}
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vd2ViLmJ4YXBpLnN4YmRqdy5jb20iLCJkYXRhIjoie1widWlkXCI6MzQxMTYwNTA3NzI4OTMzNjgzMn0iLCJpc3MiOiJodHRwOi8vd2ViLmJ4YXBpLnN4YmRqdy5jb20iLCJleHAiOjE1ODMwNTgzMTgsImlhdCI6MTU4MTU4NzA4OX0.czrE25SaWdEVnOsshYDJOJ9xiM3fw9tAg4oluJFGoOw
     */

    private String serveList;
    private String applyList;
    private UserinfoBean userinfo;
    private String token;

    public String getServeList() {
        return serveList;
    }

    public void setServeList(String serveList) {
        this.serveList = serveList;
    }

    public String getApplyList() {
        return applyList;
    }

    public void setApplyList(String applyList) {
        this.applyList = applyList;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserinfoBean {
        /**
         * status_mbvalidation : 0
         * mbid :
         * code : 200
         * uname : 13621123057
         * rongyun_token : u+9l6qg4pM/YN6Oj4uqoQqI0WQFcueaggJ4WbBYsYEuYx9PrV3O411o/DhQ7llhXz15Sea0Lc6EsO30xZ3MDhaWB2FGcSakBVHwQ5aKLdc4=
         * photo : /group1/M00/DF/91/wKg8Fl14pNeACbgtAALp47J5UC0278.jpg
         * xinge_token : 376be5d284574e7a5cf4be39f8e46c2bb5a5a9bb
         * uid : 3411605077289336832
         * company_authen_status : 1
         * subsystem_id : 100000000
         * is_boss : false
         * is_charge : 0
         * idcard : 140109199301272515
         * status_authentication : 1
         * nickname : 哈喽
         * name : 魏文强
         * rongyun_is_open : 0
         * status_jgvalidation : 0
         */

        private String status_mbvalidation;
        private String mbid;
        private String code;
        private String uname;
        private String passwd;
        private String rongyun_token;
        private String photo;
        private String xinge_token;
        private String uid;
        private String company_authen_status;
        private String subsystem_id;
        private String is_boss;
        private String is_charge;
        private String idcard;
        private String status_authentication;
        private String nickname;
        private String name;
        private String rongyun_is_open;
        private String status_jgvalidation;
        private int role_id;
        private String role_name;

        public String getStatus_mbvalidation() {
            return status_mbvalidation;
        }

        public void setStatus_mbvalidation(String status_mbvalidation) {
            this.status_mbvalidation = status_mbvalidation;
        }

        public String getMbid() {
            return mbid;
        }

        public void setMbid(String mbid) {
            this.mbid = mbid;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }
        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getRongyun_token() {
            return rongyun_token;
        }

        public void setRongyun_token(String rongyun_token) {
            this.rongyun_token = rongyun_token;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getXinge_token() {
            return xinge_token;
        }

        public void setXinge_token(String xinge_token) {
            this.xinge_token = xinge_token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCompany_authen_status() {
            return company_authen_status;
        }

        public void setCompany_authen_status(String company_authen_status) {
            this.company_authen_status = company_authen_status;
        }

        public String getSubsystem_id() {
            return subsystem_id;
        }

        public void setSubsystem_id(String subsystem_id) {
            this.subsystem_id = subsystem_id;
        }

        public String getIs_boss() {
            return is_boss;
        }

        public void setIs_boss(String is_boss) {
            this.is_boss = is_boss;
        }

        public String getIs_charge() {
            return is_charge;
        }

        public void setIs_charge(String is_charge) {
            this.is_charge = is_charge;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getStatus_authentication() {
            return status_authentication;
        }

        public void setStatus_authentication(String status_authentication) {
            this.status_authentication = status_authentication;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRongyun_is_open() {
            return rongyun_is_open;
        }

        public void setRongyun_is_open(String rongyun_is_open) {
            this.rongyun_is_open = rongyun_is_open;
        }

        public String getStatus_jgvalidation() {
            return status_jgvalidation;
        }

        public void setStatus_jgvalidation(String status_jgvalidation) {
            this.status_jgvalidation = status_jgvalidation;
        }

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }
    }
}
