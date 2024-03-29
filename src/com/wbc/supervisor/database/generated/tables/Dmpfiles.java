/**
 * This class is generated by jOOQ
 */
package com.wbc.supervisor.database.generated.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.1" },
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dmpfiles extends org.jooq.impl.TableImpl<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord> {

	private static final long serialVersionUID = 378187901;

	/**
	 * The singleton instance of <code>dashboard2.dmpfiles</code>
	 */
	public static final com.wbc.supervisor.database.generated.tables.Dmpfiles DMPFILES = new com.wbc.supervisor.database.generated.tables.Dmpfiles();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord> getRecordType() {
		return com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord.class;
	}

	/**
	 * The column <code>dashboard2.dmpfiles.dbid</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, Integer> DBID = createField("dbid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>dashboard2.dmpfiles.sig</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, String> SIG = createField("sig", org.jooq.impl.SQLDataType.VARCHAR.length(255).defaulted(true), this, "");

	/**
	 * The column <code>dashboard2.dmpfiles.eventid</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, Integer> EVENTID = createField("eventid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>dashboard2.dmpfiles.occurred</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, java.sql.Timestamp> OCCURRED = createField("occurred", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>dashboard2.dmpfiles.version</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, String> VERSION = createField("version", org.jooq.impl.SQLDataType.VARCHAR.length(25).defaulted(true), this, "");

	/**
	 * The column <code>dashboard2.dmpfiles.stringid</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, Integer> STRINGID = createField("stringid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>dashboard2.dmpfiles.lastadj</code>.
	 */
	public final org.jooq.TableField<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, java.sql.Timestamp> LASTADJ = createField("lastadj", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>dashboard2.dmpfiles</code> table reference
	 */
	public Dmpfiles() {
		this("dmpfiles", null);
	}

	/**
	 * Create an aliased <code>dashboard2.dmpfiles</code> table reference
	 */
	public Dmpfiles(String alias) {
		this(alias, com.wbc.supervisor.database.generated.tables.Dmpfiles.DMPFILES);
	}

	private Dmpfiles(String alias, org.jooq.Table<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord> aliased) {
		this(alias, aliased, null);
	}

	private Dmpfiles(String alias, org.jooq.Table<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.wbc.supervisor.database.generated.Dashboard2.DASHBOARD2, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord, Integer> getIdentity() {
		return com.wbc.supervisor.database.generated.Keys.IDENTITY_DMPFILES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord> getPrimaryKey() {
		return com.wbc.supervisor.database.generated.Keys.KEY_DMPFILES_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord>>asList(com.wbc.supervisor.database.generated.Keys.KEY_DMPFILES_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.wbc.supervisor.database.generated.tables.Dmpfiles as(String alias) {
		return new com.wbc.supervisor.database.generated.tables.Dmpfiles(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.wbc.supervisor.database.generated.tables.Dmpfiles rename(String name) {
		return new com.wbc.supervisor.database.generated.tables.Dmpfiles(name, null);
	}
}
