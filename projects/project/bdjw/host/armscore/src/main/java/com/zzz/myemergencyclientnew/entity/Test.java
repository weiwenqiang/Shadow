package com.zzz.myemergencyclientnew.entity;

import java.util.List;

public class Test {

    /**
     * code : 200
     * data : [{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/home/news","apiVersion":"v1","fragmentRoute":"/TangramFragment/fragment"},"click":"4","funType":"4","iconChecked":"http://192.168.81.137:8080/test/static/image/tabbar_icon_index_down_pink.png","iconNormal":"http://192.168.81.137:8080/test/static/image/tabbar_icon_index_normal.png","orient":"b","params":"{}","title":"新闻"},{"apiTangramList":[{"click":"1","funType":"1","params":"{}","title":"政治教育","webEntity":{"route":"/WebSingle/fragment","url":"http://edu.bxapi.sxbdjw.com/web/index.html"}},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/emergKnowl","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"应急知识"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/studentMilitary","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"学生军训"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/militiaReserve","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"民兵预备役"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/defenceEdu","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"国防教育"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/careerEarn","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"创业创收"},{"click":"1","funType":"1","params":"{}","title":"实用技术","webEntity":{"route":"/WebSingle/fragment","url":"http://www.cnncty.com/mobile/"}}],"click":"5","funType":"5","iconChecked":"http://192.168.81.137:8080/test/static/image/tabbar_icon_adde_down_pink.png","iconNormal":"http://192.168.81.137:8080/test/static/image/tabbar_icon_adde_normal.png","orient":"b","params":"{}","title":"应用"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/home/publish","apiVersion":"v1","fragmentRoute":"/TangramFragment/fragment"},"click":"4","funType":"4","iconChecked":"http://192.168.81.137:8080/test/static/image/tabbar_icon_cargo_down_pink.png","iconNormal":"http://192.168.81.137:8080/test/static/image/tabbar_icon_cargo_normal.png","orient":"b","params":"{}","title":"发布"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/home/serve","apiVersion":"v1","fragmentRoute":"/TangramFragment/fragment"},"click":"4","funType":"4","iconChecked":"http://192.168.81.137:8080/test/static/image/tabbar_icon_money_down_pink.png","iconNormal":"http://192.168.81.137:8080/test/static/image/tabbar_icon_money_normal.png","orient":"b","params":"{}","title":"服务"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/home/me","apiVersion":"v1","fragmentRoute":"/TangramFragment/fragment"},"click":"4","funType":"4","iconChecked":"http://192.168.81.137:8080/test/static/image/tabbar_icon_personal_down_pink.png","iconNormal":"http://192.168.81.137:8080/test/static/image/tabbar_icon_personal_normal.png","orient":"b","params":"{}","title":"我的"}]
     * msg :
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * apiTangramEntity : {"api":"http://192.168.81.137:9999/app/home/news","apiVersion":"v1","fragmentRoute":"/TangramFragment/fragment"}
         * click : 4
         * funType : 4
         * iconChecked : http://192.168.81.137:8080/test/static/image/tabbar_icon_index_down_pink.png
         * iconNormal : http://192.168.81.137:8080/test/static/image/tabbar_icon_index_normal.png
         * orient : b
         * params : {}
         * title : 新闻
         * apiTangramList : [{"click":"1","funType":"1","params":"{}","title":"政治教育","webEntity":{"route":"/WebSingle/fragment","url":"http://edu.bxapi.sxbdjw.com/web/index.html"}},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/emergKnowl","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"应急知识"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/studentMilitary","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"学生军训"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/militiaReserve","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"民兵预备役"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/defenceEdu","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"国防教育"},{"apiTangramEntity":{"api":"http://192.168.81.137:9999/app/apply/careerEarn","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"},"click":"4","funType":"4","params":"{}","title":"创业创收"},{"click":"1","funType":"1","params":"{}","title":"实用技术","webEntity":{"route":"/WebSingle/fragment","url":"http://www.cnncty.com/mobile/"}}]
         */

        private ApiTangramEntityBean apiTangramEntity;
        private String click;
        private String funType;
        private String iconChecked;
        private String iconNormal;
        private String orient;
        private String params;
        private String title;
        private List<ApiTangramListBean> apiTangramList;

        public ApiTangramEntityBean getApiTangramEntity() {
            return apiTangramEntity;
        }

        public void setApiTangramEntity(ApiTangramEntityBean apiTangramEntity) {
            this.apiTangramEntity = apiTangramEntity;
        }

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getFunType() {
            return funType;
        }

        public void setFunType(String funType) {
            this.funType = funType;
        }

        public String getIconChecked() {
            return iconChecked;
        }

        public void setIconChecked(String iconChecked) {
            this.iconChecked = iconChecked;
        }

        public String getIconNormal() {
            return iconNormal;
        }

        public void setIconNormal(String iconNormal) {
            this.iconNormal = iconNormal;
        }

        public String getOrient() {
            return orient;
        }

        public void setOrient(String orient) {
            this.orient = orient;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ApiTangramListBean> getApiTangramList() {
            return apiTangramList;
        }

        public void setApiTangramList(List<ApiTangramListBean> apiTangramList) {
            this.apiTangramList = apiTangramList;
        }

        public static class ApiTangramEntityBean {
            /**
             * api : http://192.168.81.137:9999/app/home/news
             * apiVersion : v1
             * fragmentRoute : /TangramFragment/fragment
             */

            private String api;
            private String apiVersion;
            private String fragmentRoute;

            public String getApi() {
                return api;
            }

            public void setApi(String api) {
                this.api = api;
            }

            public String getApiVersion() {
                return apiVersion;
            }

            public void setApiVersion(String apiVersion) {
                this.apiVersion = apiVersion;
            }

            public String getFragmentRoute() {
                return fragmentRoute;
            }

            public void setFragmentRoute(String fragmentRoute) {
                this.fragmentRoute = fragmentRoute;
            }
        }

        public static class ApiTangramListBean {
            /**
             * click : 1
             * funType : 1
             * params : {}
             * title : 政治教育
             * webEntity : {"route":"/WebSingle/fragment","url":"http://edu.bxapi.sxbdjw.com/web/index.html"}
             * apiTangramEntity : {"api":"http://192.168.81.137:9999/app/apply/emergKnowl","apiVersion":"v1","fragmentRoute":"/TangramSingle/fragment"}
             */

            private String click;
            private String funType;
            private String params;
            private String title;
            private WebEntityBean webEntity;
            private ApiTangramEntityBeanX apiTangramEntity;

            public String getClick() {
                return click;
            }

            public void setClick(String click) {
                this.click = click;
            }

            public String getFunType() {
                return funType;
            }

            public void setFunType(String funType) {
                this.funType = funType;
            }

            public String getParams() {
                return params;
            }

            public void setParams(String params) {
                this.params = params;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public WebEntityBean getWebEntity() {
                return webEntity;
            }

            public void setWebEntity(WebEntityBean webEntity) {
                this.webEntity = webEntity;
            }

            public ApiTangramEntityBeanX getApiTangramEntity() {
                return apiTangramEntity;
            }

            public void setApiTangramEntity(ApiTangramEntityBeanX apiTangramEntity) {
                this.apiTangramEntity = apiTangramEntity;
            }

            public static class WebEntityBean {
                /**
                 * route : /WebSingle/fragment
                 * url : http://edu.bxapi.sxbdjw.com/web/index.html
                 */

                private String route;
                private String url;

                public String getRoute() {
                    return route;
                }

                public void setRoute(String route) {
                    this.route = route;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class ApiTangramEntityBeanX {
                /**
                 * api : http://192.168.81.137:9999/app/apply/emergKnowl
                 * apiVersion : v1
                 * fragmentRoute : /TangramSingle/fragment
                 */

                private String api;
                private String apiVersion;
                private String fragmentRoute;

                public String getApi() {
                    return api;
                }

                public void setApi(String api) {
                    this.api = api;
                }

                public String getApiVersion() {
                    return apiVersion;
                }

                public void setApiVersion(String apiVersion) {
                    this.apiVersion = apiVersion;
                }

                public String getFragmentRoute() {
                    return fragmentRoute;
                }

                public void setFragmentRoute(String fragmentRoute) {
                    this.fragmentRoute = fragmentRoute;
                }
            }
        }
    }
}
