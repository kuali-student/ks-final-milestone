package org.kuali.student.poc.server.admin.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.poc.client.admin.MessageModelObject;
import org.kuali.student.poc.client.admin.MessagesAdminService;

public class MessagesAdminServiceImpl implements MessagesAdminService {

	@Override
	public Map<String, List<String>> getMessageGroupTree() {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		Connection conn = null;
		try {
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("select distinct locale, groupName from messages order by locale asc, groupName asc");
			ResultSet rs = stmt.executeQuery();
			String lastLocale = "";
			List<String> list = null;
			while (rs.next()) {
				String locale = rs.getString(1);
				if (!locale.equals(lastLocale)) {
					lastLocale = locale;
					list = new ArrayList<String>();
					result.put(locale, list);
				}
				list.add(rs.getString(2));
			}
			rs.close();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve message group tree", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					// eat it
				}
			}
		}
		return result;
	}

	@Override
	public List<MessageModelObject> getMessages(String locale, String groupName) {
		List<MessageModelObject> result = new ArrayList<MessageModelObject>();
		Connection conn = null;
		try {
			conn = getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("select messageId, message from messages where locale = ? and groupName = ? order by messageId asc");
			stmt.setString(1, locale);
			stmt.setString(2, groupName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				MessageModelObject m = new MessageModelObject();
				m.setLocale(locale);
				m.setGroupName(groupName);
				m.setMessageId(rs.getString(1));
				m.setMessage(rs.getString(2));
				result.add(m);
			}
			rs.close();
		} catch (Exception e) {
			throw new RuntimeException("Failed to retrieve messages", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					// eat it
				}
			}
		}
		return result;
	}

	@Override
	public Boolean update(MessageModelObject message) {
		Boolean result = true;
		Connection conn = null;
		try {
			conn = getConnection();
			result = updateMessage(conn, message);
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException("Failed to update message", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					// eat it
				}
			}
		}
		return result;
	}

	@Override
	public Boolean update(List<MessageModelObject> messages) {
		Boolean result = true;
		Connection conn = null;
		try {
			conn = getConnection();
			for (MessageModelObject message : messages) {
				result = result && updateMessage(conn, message);	
			}
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException("Failed to update messages", e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					// eat it
				}
			}
		}
		return result;
	}

	private Boolean updateMessage(Connection conn, MessageModelObject message) throws SQLException {
		//System.out.println("updating message " + message.getUniqueId() + "to " + message.getMessage());
		PreparedStatement stmt = conn.prepareStatement("update messages set message = ? where locale = ? and groupName = ? and messageId = ?");
		stmt.setString(1, message.getMessage());
		stmt.setString(2, message.getLocale());
		stmt.setString(3, message.getGroupName());
		stmt.setString(4, message.getMessageId());
		return stmt.executeUpdate() == 1;
	}
	
	private Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//System.setProperty("derby.system.home", System.getProperty("java.io.tmpdir") + "/tmp");
		System.setProperty("derby.system.home","./target");
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		
		Connection result = DriverManager.getConnection("jdbc:derby:MessagesDB;create=true");
		result.setAutoCommit(false);
		return result;
	}
}
