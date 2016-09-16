package com.example.mysql;

public class Info_Type {
	
	public enum INFOTYPE{
		
		//keep����
        brand {public String getUrl(){return "keep/change_brand.php";}public INFOTYPE getType(){return brand;}public String getName(){return "brand";}},

        logo {public String getUrl(){return "keep/change_logo.php";}public INFOTYPE getType(){return logo;}public String getName(){return "logo";}},

        platenum {public String getUrl(){return "keep/change_platenum.php";}public INFOTYPE getType(){return platenum;}public String getName(){return "platenum";}},

        enginenum {public String getUrl(){return "keep/change_enginenum.php";}public INFOTYPE getType(){return enginenum;}public String getName(){return "enginenum";}},

        carlevel {public String getUrl(){return "keep/change_carlevel.php";}public INFOTYPE getType(){return carlevel;}public String getName(){return "carlevel";}},

        colometer {public String getUrl(){return "keep/change_colometer.php";}public INFOTYPE getType(){return colometer;}public String getName(){return "colometer";}},
        
        enginestate {public String getUrl(){return "keep/change_enginestate.php";}public INFOTYPE getType(){return enginestate;}public String getName(){return "enginestate";}},
        
        shiftstate {public String getUrl(){return "keep/change_shiftstate.php";}public INFOTYPE getType(){return shiftstate;}public String getName(){return "shiftstate";}},
        
        light {public String getUrl(){return "keep/change_light.php";}public INFOTYPE getType(){return light;}public String getName(){return "light";}},

        oilcount {public String getUrl(){return "keep/change_oilcount.php";}public INFOTYPE getType(){return oilcount;}public String getName(){return "oilcount";}},
        
        //orderԤԼ
        order_time {public String getUrl(){return "order/change_time.php";}public INFOTYPE getType(){return order_time;}public String getName(){return "order_time";}},
        
        gas_station {public String getUrl(){return "order/change_gasstation.php";}public INFOTYPE getType(){return gas_station;}public String getName(){return "gas_station";}},
        
        gas_type {public String getUrl(){return "order/change_gastype.php";}public INFOTYPE getType(){return gas_type;}public String getName(){return "gas_type";}},
        
        gas_num {public String getUrl(){return "order/change_gasnum.php";}public INFOTYPE getType(){return gas_num;}public String getName(){return "gas_num";}},
        
        //�û�
        user_name {public String getUrl(){return "user/change_nickname.php";}public INFOTYPE getType(){return user_name;}public String getName(){return "user_name";}},
        
        user_password {public String getUrl(){return "user/change_password.php";}public INFOTYPE getType(){return user_password;}public String getName(){return "user_password";}},
        
        user_image_head {public String getUrl(){return "user/change_head.php";}public INFOTYPE getType(){return user_image_head;}public String getName(){return "user_image_head";}},
        
        user_age {public String getUrl(){return "user/change_age.php";}public INFOTYPE getType(){return user_age;}public String getName(){return "user_age";}},
        
        user_sex {public String getUrl(){return "user/change_sex.php";}public INFOTYPE getType(){return user_sex;}public String getName(){return "user_sex";}},
        
        user_schoolname {public String getUrl(){return "user/change_schoolname.php";}public INFOTYPE getType(){return user_schoolname;}public String getName(){return "user_schoolname";}},
        
        user_sign {public String getUrl(){return "user/change_sign.php";}public INFOTYPE getType(){return user_sign;}public String getName(){return "user_sign";}},
        
        user_signtime {public String getUrl(){return "user/change_signtime.php";}public INFOTYPE getType(){return user_signtime;}public String getName(){return "user_signtime";}},
        
        user_bg {public String getUrl(){return "user/change_bg.php";}public INFOTYPE getType(){return user_bg;}public String getName(){return "user_bg";}};
        public abstract INFOTYPE getType();
        public abstract String getName();
        public abstract String getUrl();
	}

}
