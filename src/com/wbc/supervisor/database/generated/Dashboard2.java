/**
 * This class is generated by jOOQ
 */
package com.wbc.supervisor.database.generated;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.1" },
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dashboard2 extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 1803825656;

	/**
	 * The singleton instance of <code>dashboard2</code>
	 */
	public static final Dashboard2 DASHBOARD2 = new Dashboard2();

	/**
	 * No further instances allowed
	 */
	private Dashboard2() {
		super("dashboard2");
	}

	@Override
	public final java.util.List<org.jooq.Table<?>> getTables() {
		java.util.List result = new java.util.ArrayList();
		//result.addAll(getTables0());
		return result;
	}

	/*private final java.util.List<org.jooq.Table<?>> getTables0() {
		return java.util.Arrays.<org.jooq.Table<?>>asList(
			com.wbc.supervisor.database.supervisor.database.generated.tables.Deviceinfo.DEVICEINFO,
			com.wbc.supervisor.database.supervisor.database.generated.tables.Devicenameinfo.DEVICENAMEINFO,
			com.wbc.supervisor.database.supervisor.database.generated.tables.Dmpfiles.DMPFILES,
			com.wbc.supervisor.database.supervisor.database.generated.tables.Properties.PROPERTIES,
			com.wbc.supervisor.database.supervisor.database.generated.tables.Stats.STATS,
			com.wbc.supervisor.database.supervisor.database.generated.tables.Thresholds.THRESHOLDS,
			com.wbc.supervisor.database.supervisor.database.generated.tables.Topoinfo.TOPOINFO,
			com.wbc.supervisor.database.supervisor.database.generated.tables.Vendormacs.VENDORMACS);
	}*/
}
