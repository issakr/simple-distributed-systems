package vs.anzeigetafel.view;

import java.util.ResourceBundle;

public enum View {
	LOGIN{
		@Override
		public String getTitel() {
			return "Login View";
		}
		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
		@Override
		public String getCssFile() {
			return "/css/LoginStyles.css";
		}
		@Override
		public Double getwidth() {
			return 500.0;
		}
		@Override
		public Double getheight() {
			return 500.0;
		}
	},
	TAFEL{

		@Override
		public String getTitel() {
			return"Tafel View";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Tafel.fxml";
		}

		@Override
		public String getCssFile() {
			return "/css/tafelStyles.css";
		}

		@Override
		public Double getwidth() {
			return 800.0;
		}

		@Override
		public Double getheight() {
			return 650.0;
		}
		
	},
	VERWALTUNG{

		@Override
		public String getTitel() {
			return"Verwaltung View";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Verwaltung.fxml";
		}

		@Override
		public String getCssFile() {
			return "/css/verwaltungStyles.css";
		}

		@Override
		public Double getwidth() {
			return 800.0;
		}

		@Override
		public Double getheight() {
			return 650.0;
		}
		
	},
	NEWMSG{

		@Override
		public String getTitel() {
			return"neuMSG View";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/neuMSG.fxml";
		}

		@Override
		public String getCssFile() {
			return "/css/neuMsgStyles.css";
		}

		@Override
		public Double getwidth() {
			return 800.0;
		}

		@Override
		public Double getheight() {
			return 500.0;
		}
		
	},
	CHANGEMSG{
		@Override
		public String getTitel() {
			return"aendere Nachricht";
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ChangeNachricht.fxml";
		}

		@Override
		public String getCssFile() {
			return "/css/ChangeNachrichtStyles.css";
		}

		@Override
		public Double getwidth() {
			return 800.0;
		}

		@Override
		public Double getheight() {
			return 500.0;
		}
	};
	public abstract String getTitel();
	public abstract String getFxmlFile();
	public abstract String getCssFile();
	public abstract Double getwidth();
	public abstract Double getheight();


	String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
