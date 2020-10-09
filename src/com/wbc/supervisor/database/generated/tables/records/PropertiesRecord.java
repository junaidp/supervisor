/**
 * This class is generated by jOOQ
 */
package com.wbc.supervisor.database.generated.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.1" },
                            comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PropertiesRecord extends org.jooq.impl.UpdatableRecordImpl<com.wbc.supervisor.database.generated.tables.records.PropertiesRecord> implements org.jooq.Record5<Integer, String, String, String, String> {

	private static final long serialVersionUID = 557422337;

	/**
	 * Setter for <code>dashboard2.properties.pid</code>.
	 */
	public void setPid(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>dashboard2.properties.pid</code>.
	 */
	public Integer getPid() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>dashboard2.properties.type</code>.
	 */
	public void setType(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>dashboard2.properties.type</code>.
	 */
	public String getType() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>dashboard2.properties.name</code>.
	 */
	public void setName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>dashboard2.properties.name</code>.
	 */
	public String getName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>dashboard2.properties.id</code>.
	 */
	public void setId(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>dashboard2.properties.id</code>.
	 */
	public String getId() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>dashboard2.properties.value</code>.
	 */
	public void setValue(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>dashboard2.properties.value</code>.
	 */
	public String getValue() {
		return (String) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<Integer, String, String, String, String> fieldsRow() {
		return (org.jooq.Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row5<Integer, String, String, String, String> valuesRow() {
		return (org.jooq.Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Integer> field1() {
		return com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES.PID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field2() {
		return com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES.TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field3() {
		return com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field4() {
		return com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field5() {
		return com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES.VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getPid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesRecord value1(Integer value) {
		setPid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesRecord value2(String value) {
		setType(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesRecord value3(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesRecord value4(String value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesRecord value5(String value) {
		setValue(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PropertiesRecord values(Integer value1, String value2, String value3, String value4, String value5) {
		return this;
	}

	@Override
	public Integer component1() {
		return null;
	}

	@Override
	public String component2() {
		return null;
	}

	@Override
	public String component3() {
		return null;
	}

	@Override
	public String component4() {
		return null;
	}

	@Override
	public String component5() {
		return null;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PropertiesRecord
	 */
	public PropertiesRecord() {
		super(com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES);
	}

	/**
	 * Create a detached, initialised PropertiesRecord
	 */
	public PropertiesRecord(Integer pid, String type, String name, String id, String value) {
		super(com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES);

		setValue(0, pid);
		setValue(1, type);
		setValue(2, name);
		setValue(3, id);
		setValue(4, value);
	}
}