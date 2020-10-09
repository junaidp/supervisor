package com.wbc.supervisor.client.dashboardutilities;

import com.google.gwt.i18n.client.Messages;

public interface DashboardUtilMessages extends Messages
{
	@DefaultMessage("Connection")
	String id_connection();
	@DefaultMessage("Home")
	String id_home();
	@DefaultMessage("Settings")
	String id_settings();
	@DefaultMessage("Utilities")
	String id_utilities();
	@DefaultMessage("Help")
	String id_help();
	String id_device_changed_mac();
	String id_device_changed_ip();
	String id_scanner_stopped();
	String id_ping_response();
	String id_bandwidth_threshold();
	String id_device_connection();
	String id_device_moved();

	String id_SNMP_lost_returned();

	String id_device_joined_network();

	String id_device_changed_speed();

	String id_device_changed_ENIP();

	String id_scanner_version();

	String id_restored_database();

	String id_registration();

	String id_system_configuration_changes_applied();

	String id_device_moved_to_subnetparent();

	String id_SNMP_supported();

	String id_admin_verified();

	String id_switch_reports_mac();

	String id_deleted_child_node();

	String id_deleted_node();

	String id_admin_UN_verified();

	String id_automatic_backup_file_deleted();

	String id_adjusting_scanrange_to_add_top_parent();

	String id_admin_moved_child_node();

	String id_deleted_top_parent();

	String id_added_child_node();

	String id_merging_device();

	String id_device_unverified();

	String id_merging_device_into();

	String id_not_under_a_support();

	String id_interim_kpi_version();

	String id_error_connecting();

	String id_trap();

	String id_delete_change_name();

	String id_LLDP_Device_moved();

	String id_counter_for_IP();

	String id_automatic_backup_file_created();

	String id_Joined_as_top_parent();

	String id_Error_occurred_while_processing_info();

	String id_Unsupported_kpi_version();

	String id_general_description();

	String id_general_switchIpAddress();

	String id_general_idCommunity();

	String id_general_location();

	String id_general_router();

	String id_general_ifToGateway();

	String id_general_numIps();

	String id_general_numIfs();

	String id_general_numArps();

	String id_general_numMacs();

    String id_true();

    String id_false();

	String id_critical0();
	String id_critical1();
	String id_critical2();
	String id_critical3();

	String id_intravueHostOS();
	String id_intravueStatus();
	String id_statusMessage();
	String id_scannerSpeed();
	String id_foundDevices();
	String id_hostNIC1Ip();
	String id_hostNIC2Ip();
	String id_hostNIC1Mac();
	String id_hostNIC2Mac();
}
