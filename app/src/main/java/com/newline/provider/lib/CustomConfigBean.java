package com.newline.provider.lib;

import java.util.List;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTProvider
 * @email momo.weiye@gmail.com
 * @time 2020/4/17 14:11
 * @describe
 */
public class CustomConfigBean {


    /**
     * system : {"key":"theme_type;logo_enable","value":"20;1"}
     * secure : {"key":"","value":""}
     * source : {"key":"","value":""}
     * whiteboard : {"key":"","value":""}
     * toolbar : {"key":"","value":""}
     * other : {"key":"","value":""}
     * app : {"key":"","value":""}
     * off_time : {"value":[{"_id":1,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":0,"sat":1,"hour":20,"min":20,"timeEnable":1,"startSource":"pc"},{"_id":2,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":1,"sat":1,"hour":22,"min":59,"timeEnable":1},{"_id":3,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":0,"sat":0,"hour":11,"min":33,"timeEnable":1,"startSource":"vga"}]}
     * on_time : {"value":[{"_id":1,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":0,"sat":1,"hour":20,"min":20,"timeEnable":1,"startSource":"pc"},{"_id":2,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":1,"sat":1,"hour":22,"min":59,"timeEnable":1},{"_id":3,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":0,"sat":0,"hour":11,"min":33,"timeEnable":1,"startSource":"vga"}]}
     */

    private SystemBean system;
    private SecureBean secure;
    private SourceBean source;
    private WhiteboardBean whiteboard;
    private ToolbarBean toolbar;
    private OtherBean other;
    private AppBean app;
    private OffTimeBean off_time;
    private OnTimeBean on_time;

    public SystemBean getSystem() {
        return system;
    }

    public void setSystem(SystemBean system) {
        this.system = system;
    }

    public SecureBean getSecure() {
        return secure;
    }

    public void setSecure(SecureBean secure) {
        this.secure = secure;
    }

    public SourceBean getSource() {
        return source;
    }

    public void setSource(SourceBean source) {
        this.source = source;
    }

    public WhiteboardBean getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(WhiteboardBean whiteboard) {
        this.whiteboard = whiteboard;
    }

    public ToolbarBean getToolbar() {
        return toolbar;
    }

    public void setToolbar(ToolbarBean toolbar) {
        this.toolbar = toolbar;
    }

    public OtherBean getOther() {
        return other;
    }

    public void setOther(OtherBean other) {
        this.other = other;
    }

    public AppBean getApp() {
        return app;
    }

    public void setApp(AppBean app) {
        this.app = app;
    }

    public OffTimeBean getOff_time() {
        return off_time;
    }

    public void setOff_time(OffTimeBean off_time) {
        this.off_time = off_time;
    }

    public OnTimeBean getOn_time() {
        return on_time;
    }

    public void setOn_time(OnTimeBean on_time) {
        this.on_time = on_time;
    }

    public static class SystemBean {
        /**
         * key : theme_type;logo_enable
         * value : 20;1
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SecureBean {
        /**
         * key :
         * value :
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class SourceBean {
        /**
         * key :
         * value :
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class WhiteboardBean {
        /**
         * key :
         * value :
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ToolbarBean {
        /**
         * key :
         * value :
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class OtherBean {
        /**
         * key :
         * value :
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class AppBean {
        /**
         * key :
         * value :
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class OffTimeBean {
        /**
         * value : [{"_id":1,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":0,"sat":1,"hour":20,"min":20,"timeEnable":1,"startSource":"pc"},{"_id":2,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":1,"sat":1,"hour":22,"min":59,"timeEnable":1},{"_id":3,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":0,"sat":0,"hour":11,"min":33,"timeEnable":1,"startSource":"vga"}]
         */

        private List<HHTPowerTime> value;


        public List<HHTPowerTime> getValue() {
            return value;
        }

        public void setValue(List<HHTPowerTime> value) {
            this.value = value;
        }

    }

    public static class OnTimeBean {
        /**
         * value : [{"_id":1,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":0,"sat":1,"hour":20,"min":20,"timeEnable":1,"startSource":"pc"},{"_id":2,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":1,"sat":1,"hour":22,"min":59,"timeEnable":1},{"_id":3,"sun":0,"mon":0,"tues":0,"wed":0,"thur":0,"fri":0,"sat":0,"hour":11,"min":33,"timeEnable":1,"startSource":"vga"}]
         */

        private List<HHTPowerTime> value;


        public List<HHTPowerTime> getValue() {
            return value;
        }

        public void setValue(List<HHTPowerTime> value) {
            this.value = value;
        }



    }
}
