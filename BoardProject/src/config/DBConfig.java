package config;
public interface DBConfig {
	public String DB_DRIVER = "oracle.jdbc.OracleDriver";
	public String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public String DB_USER = "system";
	public String DB_PASS= "123456";
}
