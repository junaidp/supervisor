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
public class TopoinfoRecord extends org.jooq.impl.UpdatableRecordImpl<com.wbc.supervisor.database.generated.tables.records.TopoinfoRecord> implements org.jooq.Record8<Integer, Integer, Integer, String, Integer, String, Integer, Integer> {

	private static final long serialVersionUID = -2043059208;

	/**
	 * Setter for <code>dashboard2.topoinfo.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>dashboard2.topoinfo.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>dashboard2.topoinfo.parent</code>.
	 */
	public void setParent(Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>dashboard2.topoinfo.parent</code>.
	 */
	public Integer getParent() {
		return (Integer) getValue(1);
	}

	/**
	 * Setter for <code>dashboard2.topoinfo.child</code>.
	 */
	public void setChild(Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>dashboard2.topoinfo.child</code>.
	 */
	public Integer getChild() {
		return (Integer) getValue(2);
	}

	/**
	 * Setter for <code>dashboard2.topoinfo.childIp</code>.
	 */
	public void setChildip(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>dashboard2.topoinfo.childIp</code>.
	 */
	public String getChildip() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>dashboard2.topoinfo.networkid</code>.
	 */
	public void setNetworkid(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>dashboard2.topoinfo.networkid</code>.
	 */
	public Integer getNetworkid() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>dashboard2.topoinfo.name</code>.
	 */
	public void setName(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>dashboard2.topoinfo.name</code>.
	 */
	public String getName() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>dashboard2.topoinfo.portno</code>.
	 */
	public void setPortno(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>dashboard2.topoinfo.portno</code>.
	 */
	public Integer getPortno() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>dashboard2.topoinfo.upportno</code>.
	 */
	public void setUpportno(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>dashboard2.topoinfo.upportno</code>.
	 */
	public Integer getUpportno() {
		return (Integer) getValue(7);
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
	// Record8 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row8<Integer, Integer, Integer, String, Integer, String, Integer, Integer> fieldsRow() {
		return (org.jooq.Row8) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row8<Integer, Integer, Integer, String, Integer, String, Integer, Integer> valuesRow() {
		return (org.jooq.Row8) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Integer> field1() {
		return com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Integer> field2() {
		return com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.PARENT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Integer> field3() {
		return com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.CHILD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field4() {
		return com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.CHILDIP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Integer> field5() {
		return com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.NETWORKID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<String> field6() {
		return com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Integer> field7() {
		return com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.PORTNO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<Integer> field8() {
		return com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO.UPPORTNO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value2() {
		return getParent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value3() {
		return getChild();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getChildip();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value5() {
		return getNetworkid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getPortno();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getUpportno();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord value2(Integer value) {
		setParent(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord value3(Integer value) {
		setChild(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord value4(String value) {
		setChildip(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord value5(Integer value) {
		setNetworkid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord value6(String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord value7(Integer value) {
		setPortno(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord value8(Integer value) {
		setUpportno(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TopoinfoRecord values(Integer value1, Integer value2, Integer value3, String value4, Integer value5, String value6, Integer value7, Integer value8) {
		return this;
	}

	@Override
	public Integer component1() {
		return null;
	}

	@Override
	public Integer component2() {
		return null;
	}

	@Override
	public Integer component3() {
		return null;
	}

	@Override
	public String component4() {
		return null;
	}

	@Override
	public Integer component5() {
		return null;
	}

	@Override
	public String component6() {
		return null;
	}

	@Override
	public Integer component7() {
		return null;
	}

	@Override
	public Integer component8() {
		return null;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TopoinfoRecord
	 */
	public TopoinfoRecord() {
		super(com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO);
	}

	/**
	 * Create a detached, initialised TopoinfoRecord
	 */
	public TopoinfoRecord(Integer id, Integer parent, Integer child, String childip, Integer networkid, String name, Integer portno, Integer upportno) {
		super(com.wbc.supervisor.database.generated.tables.Topoinfo.TOPOINFO);

		setValue(0, id);
		setValue(1, parent);
		setValue(2, child);
		setValue(3, childip);
		setValue(4, networkid);
		setValue(5, name);
		setValue(6, portno);
		setValue(7, upportno);
	}
}
