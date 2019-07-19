package com.jusdt.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.bbd.utils.config.ConfigUtil;
import info.bbd.utils.log.LogbackUtil;

public class DbcpClient {

	private static final Logger logger = LoggerFactory.getLogger(DbcpClient.class);
	private BasicDataSource dataSource;
	private String dbUrl;
	private String dbUsername;
	private String dbPassword;

	public DbcpClient() {
		dbInit();
		dbConnection();
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		try {
			dataSource.close();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			logger.info("Db close error.");
		}
	}

	/**
	 * 链接数据库
	 */
	private void dbConnection() {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);

	}

	/**
	 * 初始化数据库相关参数
	 */
	private void dbInit() {
		Properties props = ConfigUtil.getProps("conf.properties");
		dbUrl = props.getProperty("db.url");
		dbUsername = props.getProperty("db.username");
		dbPassword = props.getProperty("db.password");
	}

	public void deleteBarfooChildsite(String name) {
		String sql = "UPDATE `barfoo_childsite` SET `name` = null";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			preparedStatement.execute();
		} catch (Exception e) {

			logger.error("deleteBarfooChildsite Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	/**
	 * 获取链接
	 */
	private Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (Exception e) {
			logger.error("getConnection Exception:{}", LogbackUtil.expection2Str(e));
		}

		return null;
	}

	/*
	 * public List<RecordInfo> getSpiderList() { List<RecordInfo> result = new
	 * ArrayList<RecordInfo>(); String sql =
	 * "select id,name,age,email from students"; RecordInfo record = null; try
	 * (Connection conn = getConnection(); Statement stmt =
	 * conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) { while
	 * (rs.next()) { record = new RecordInfo(); record.setId(rs.getInt("id"));
	 * record.setName(rs.getString("name")); record.setAge(rs.getInt("age"));
	 * record.setEmail(rs.getString("email")); try { //
	 * record.setId(rs.getInt("extid")); } catch (Exception e) {
	 *
	 * } result.add(record); } } catch (Exception e) {
	 * logger.error("getSpiderList Exception:{}", LogbackUtil.expection2Str(e));
	 * } return result; }
	 */

	public void insertSiteicp(String host, String hostname, String httphost) {
		String sql = "INSERT INTO `site_icp` (`host`,`hostname`,`httphost`,`unit`,`unit_type`,`license`,`home_page`,`audit_time`,`isbbs`,`area`,`areacode`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, host);
			preparedStatement.setString(2, hostname);
			preparedStatement.setString(3, httphost);
			preparedStatement.setString(5, "");
			preparedStatement.setString(6, "");
			preparedStatement.setString(7, "");
			preparedStatement.setString(8, "");
			preparedStatement.setInt(9, 0);
			preparedStatement.setString(10, "");
			preparedStatement.setString(11, "");
			preparedStatement.execute();
		} catch (Exception e) {
			logger.error("insertSiteicp Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	public List<BarfoochildSiteInfo> selectBarfooChildSiteList() {
		List<BarfoochildSiteInfo> result = new ArrayList<>();
		String sql = "SELECT `Url` FROM `barfoo_childsite`";
		BarfoochildSiteInfo barfoochildSiteInfo = null;
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);) {
			while (resultSet.next()) {
				barfoochildSiteInfo = new BarfoochildSiteInfo();
				barfoochildSiteInfo.setUrl(resultSet.getString("url"));
				result.add(barfoochildSiteInfo);
			}
		} catch (Exception e) {
			logger.error("selectSiteList Exception:{}", LogbackUtil.expection2Str(e));
		}

		return result;
	}

	public List<SiteicpSiteInfo> selectSiteList() {
		List<SiteicpSiteInfo> result = new ArrayList<>();
		String sql = "SELECT `Name`,`Url` FROM `barfoo_childsite`";
		SiteicpSiteInfo siteInfo = null;
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);) {
			while (resultSet.next()) {
				siteInfo = new SiteicpSiteInfo();
				siteInfo.setName(resultSet.getString("Name"));
				siteInfo.setUrl(resultSet.getString("Url"));
				result.add(siteInfo);
			}
		} catch (Exception e) {
			logger.error("selectSiteList Exception:{}", LogbackUtil.expection2Str(e));
		}

		return result;
	}

	@Override
	public String toString() {
		return "JdbcTest [dbUrl=" + dbUrl + ", dbUsername=" + dbUsername + ", dbPassword=" + dbPassword + "]";
	}

	public void updateSiteicpHost(String host) {
		//提取表site_icp中host字段中的域名信息
		String sql = "UPDATE `site_icp` SET `host` = SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING_INDEX(`host`, '/', 3), '/', -1) ,':',1)";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.execute();
		} catch (Exception e) {
			logger.error("updateSiteicp Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	public void updateSiteicpHostname(String hostname) {
		//提取表site_icp中hostname字段中下划线之前的字符
		String sql = "UPDATE `site_icp` SET `hostname` = substring_index(`hostname`,'_',1) ";
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.execute();
		} catch (Exception e) {
			logger.error("updateSiteicp Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	/*
	 * // 删除数据 public void deleteSprider(int id) { String sql =
	 * "delete from student where id = ?"; try (Connection connection =
	 * getConnection(); PreparedStatement preparedStatement =
	 * connection.prepareStatement(sql);
	 *
	 * ) { preparedStatement.setInt(1, id); preparedStatement.executeUpdate();
	 * preparedStatement.close(); connection.close();
	 *
	 * } catch (Exception e) { logger.error("deleteSpriderList Excdeption:{}",
	 * LogbackUtil.expection2Str(e)); } }
	 *
	 * // 修改数据 public void updateSprider() { String sql =
	 * "update student set email = 'wwj@jusdt.cn' where id = ?"; try (Connection
	 * connection = getConnection(); PreparedStatement preparedStatement =
	 * connection.prepareStatement(sql);) { preparedStatement.setInt(1, 1);
	 * preparedStatement.execute();
	 *
	 * } catch (Exception e) { logger.error("updateSpriderList Exception:{}",
	 * LogbackUtil.expection2Str(e)); }
	 *
	 * }
	 *
	 * // 查询数据 public String selectSprider() { String name = null; String sql =
	 * "select * from student"; try (Connection connection = getConnection();
	 * Statement statement = connection.createStatement(); ResultSet resultSet =
	 * statement.executeQuery(sql);) { while (resultSet.next()) { name =
	 * resultSet.getString("name"); } } catch (Exception e) {
	 * logger.error("getName Exception:{}", LogbackUtil.expection2Str(e)); }
	 *
	 * return name; }
	 */

}