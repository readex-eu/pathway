package pathway.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import org.apache.commons.collections.list.SetUniqueList;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.remote.core.IRemoteConnection;
import org.eclipse.remote.core.IRemoteConnectionWorkingCopy;
import org.eclipse.remote.core.IRemoteServices;
import org.eclipse.remote.core.RemoteServices;
import org.eclipse.remote.core.exception.RemoteConnectionException;
import org.eclipse.remote.ui.IRemoteUIConnectionManager;
import org.eclipse.remote.ui.IRemoteUIConnectionWizard;
import org.eclipse.remote.ui.IRemoteUIServices;
import org.eclipse.remote.ui.RemoteUIServices;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.orm.PersistentException;
import org.orm.PersistentTransaction;
import pathway.PAThWayPlugin;
import pathway.data.persistence.HPCSystem;
import pathway.data.persistence.HPCSystem_CPU;
import pathway.data.persistence.PathwayPersistentManager;
import pathway.eclipse.UIUtils;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.eclipse.remotetools.ConnectionManager;
import pathway.eclipse.views.ExecutionParameters;




public class EditHPCSystems extends JDialog {
	private final JPanel mainContentTab = new JPanel();
	private JComboBox hpcSystemComboBox;

	private Map<String, HPCSystem> hpcSystemsInDB;
	private List<String> availBatchSchedulers;
	private JTextField txtOrganization;
	private JTextField txtWebsite;

	private JComboBox comboBatchScheduler;
	private JButton buttonEditConnection;
	private JPanel extraInfoTab;
	private JTabbedPane middlePanel;
	private JTextField txtProcType;
	private JTextField txtTotalCores;
	private JTextField txtProcPerNode;
	private JTextField txtCoresPerSocket;
	private JTextField txtNodes;
	private JTextField txtPeakPerf;
	private JTextField txtPeakPerfPerCore;
	private JTextField txtMemPerCore;
	private JTextField txtNetTech;
	private JTextField txtTopology;
	private JTextField txtMemoryChannels;
	private JTextField txtBandwidth;
	private JTextField txtFileSystem;
	private JTextField txtMaxCpuFreq;
	private JTextField txtMoreCPUInfo;
	private JTextField txtProcModel;
	private JTextField txtProcArch;


	public static void showDialog()
	{
		try {
			EditHPCSystems dialog = new EditHPCSystems();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditHPCSystems()
	{
		initializeHPCSystemsList();
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditHPCSystems.class.getResource("/icons/browser_icon.png")));
		setTitle("Edit HPC Systems");
		setBounds(100, 100, 767, 478);
		getContentPane().setLayout(new BorderLayout());
		{
			middlePanel = new JTabbedPane(JTabbedPane.BOTTOM);
			middlePanel.setBorder(null);
			getContentPane().add(middlePanel, BorderLayout.CENTER);
			middlePanel.addTab("Main", null, mainContentTab, "Connection information for the selected HPC system");
			middlePanel.setEnabledAt(0, true);
			mainContentTab.setBorder(new EmptyBorder(5, 5, 5, 5));
			GridBagLayout gbl_mainContentTab = new GridBagLayout();
			gbl_mainContentTab.columnWidths = new int[] {0, 267, 41, 61, 0};
			gbl_mainContentTab.rowHeights = new int[] { 30, 30, 30, 10, 0, 30, 30, 30, 30, 30, 0 };
			gbl_mainContentTab.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
			gbl_mainContentTab.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			mainContentTab.setLayout(gbl_mainContentTab);
			{
				JLabel lblOrganization = new JLabel("Organization:");
				GridBagConstraints gbc_lblOrganization = new GridBagConstraints();
				gbc_lblOrganization.anchor = GridBagConstraints.WEST;
				gbc_lblOrganization.insets = new Insets(5, 0, 5, 5);
				gbc_lblOrganization.gridx = 0;
				gbc_lblOrganization.gridy = 0;
				mainContentTab.add(lblOrganization, gbc_lblOrganization);
			}
			{
				txtOrganization = new JTextField();
				GridBagConstraints gbc_txtOrganization = new GridBagConstraints();
				gbc_txtOrganization.insets = new Insets(5, 0, 5, 5);
				gbc_txtOrganization.fill = GridBagConstraints.BOTH;
				gbc_txtOrganization.gridx = 1;
				gbc_txtOrganization.gridy = 0;
				mainContentTab.add(txtOrganization, gbc_txtOrganization);
				txtOrganization.setColumns(10);
			}
			{
				JLabel lblWebsite = new JLabel("Website:");
				GridBagConstraints gbc_lblWebsite = new GridBagConstraints();
				gbc_lblWebsite.anchor = GridBagConstraints.WEST;
				gbc_lblWebsite.insets = new Insets(5, 0, 5, 5);
				gbc_lblWebsite.gridx = 0;
				gbc_lblWebsite.gridy = 1;
				mainContentTab.add(lblWebsite, gbc_lblWebsite);
			}
			{
				txtWebsite = new JTextField();
				GridBagConstraints gbc_txtWebsite = new GridBagConstraints();
				gbc_txtWebsite.insets = new Insets(5, 0, 5, 5);
				gbc_txtWebsite.fill = GridBagConstraints.BOTH;
				gbc_txtWebsite.gridx = 1;
				gbc_txtWebsite.gridy = 1;
				mainContentTab.add(txtWebsite, gbc_txtWebsite);
				txtWebsite.setColumns(10);
			}
			{
				JButton btnNewButton = new JButton("Open");
				btnNewButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (txtWebsite.getText() != null && !txtWebsite.getText().isEmpty()) {
							Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
							if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE) ) {
								try {
									desktop.browse(new URI(txtWebsite.getText()));
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				});
				btnNewButton.setFont(new Font("Dialog", Font.ITALIC, 12));
				GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
				gbc_btnNewButton.anchor = GridBagConstraints.WEST;
				gbc_btnNewButton.gridwidth = 2;
				gbc_btnNewButton.insets = new Insets(5, 0, 5, 5);
				gbc_btnNewButton.gridx = 2;
				gbc_btnNewButton.gridy = 1;
				mainContentTab.add(btnNewButton, gbc_btnNewButton);
			}
            {
                JLabel lblEditConnection = new JLabel("Connection data:");
                GridBagConstraints gbc_lblEditConn = new GridBagConstraints();
                gbc_lblEditConn.anchor = GridBagConstraints.EAST;
                gbc_lblEditConn.insets = new Insets(5, 0, 0, 5);
                gbc_lblEditConn.gridx = 0;
                gbc_lblEditConn.gridy = 2;
                mainContentTab.add(lblEditConnection, gbc_lblEditConn);
            }
            {
                buttonEditConnection = new JButton("Edit connection...");
                buttonEditConnection.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        String hpcSystemName = hpcSystemComboBox.getSelectedItem().toString();
                        if( hpcSystemName == null || hpcSystemName.isEmpty() )
                            return;

                        EditConnectionRunnable runnable = new EditConnectionRunnable(hpcSystemName);
                        PlatformUI.getWorkbench().getDisplay().syncExec(runnable);
                    }
                });

                GridBagConstraints gbc_editConn = new GridBagConstraints();
                gbc_editConn.insets = new Insets(5, 0, 0, 5);
                gbc_editConn.fill = GridBagConstraints.HORIZONTAL;
                gbc_editConn.gridx = 1;
                gbc_editConn.gridy = 2;
                mainContentTab.add(buttonEditConnection, gbc_editConn);
            }
			{
				JLabel lblBatchScheduler = new JLabel("Batch Scheduler:");
				GridBagConstraints gbc_lblBatchScheduler = new GridBagConstraints();
				gbc_lblBatchScheduler.anchor = GridBagConstraints.EAST;
				gbc_lblBatchScheduler.insets = new Insets(5, 0, 0, 5);
				gbc_lblBatchScheduler.gridx = 0;
				gbc_lblBatchScheduler.gridy = 3;
				mainContentTab.add(lblBatchScheduler, gbc_lblBatchScheduler);
			}
			{
				comboBatchScheduler = new JComboBox();
				comboBatchScheduler.setModel(new DefaultComboBoxModel(SetUniqueList.decorate(availBatchSchedulers).toArray()));
				GridBagConstraints gbc_comboBox = new GridBagConstraints();
				gbc_comboBox.insets = new Insets(5, 0, 0, 5);
				gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
				gbc_comboBox.gridx = 1;
				gbc_comboBox.gridy = 3;
				mainContentTab.add(comboBatchScheduler, gbc_comboBox);
			}
			{
				extraInfoTab = new JPanel();
				middlePanel.addTab("Extra Information", null, extraInfoTab, "Additional information about the selected HPC system");
				GridBagLayout gbl_extraInfoTab = new GridBagLayout();
				gbl_extraInfoTab.columnWidths = new int[]{174, 192, 212, 130, 0};
				gbl_extraInfoTab.rowHeights = new int[]{10, 30, 30, 0, 30, 30, 10, 30, 30, 30, 10, 30, 30, 0};
				gbl_extraInfoTab.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
				gbl_extraInfoTab.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
				extraInfoTab.setLayout(gbl_extraInfoTab);
				{
					JLabel lblProcessor = new JLabel("Processor:");
					GridBagConstraints gbc_lblProcessor = new GridBagConstraints();
					gbc_lblProcessor.anchor = GridBagConstraints.EAST;
					gbc_lblProcessor.fill = GridBagConstraints.VERTICAL;
					gbc_lblProcessor.insets = new Insets(5, 0, 5, 5);
					gbc_lblProcessor.gridx = 0;
					gbc_lblProcessor.gridy = 1;
					extraInfoTab.add(lblProcessor, gbc_lblProcessor);
				}
				{
					txtProcType = new JTextField();
					txtProcType.setColumns(10);
					GridBagConstraints gbc_txtProcessorType = new GridBagConstraints();
					gbc_txtProcessorType.insets = new Insets(5, 0, 5, 5);
					gbc_txtProcessorType.fill = GridBagConstraints.BOTH;
					gbc_txtProcessorType.gridx = 1;
					gbc_txtProcessorType.gridy = 1;
					extraInfoTab.add(txtProcType, gbc_txtProcessorType);
				}
				{
					txtProcModel = new JTextField();
					GridBagConstraints gbc_txtProcModel = new GridBagConstraints();
					gbc_txtProcModel.insets = new Insets(5, 0, 5, 5);
					gbc_txtProcModel.fill = GridBagConstraints.BOTH;
					gbc_txtProcModel.gridx = 2;
					gbc_txtProcModel.gridy = 1;
					extraInfoTab.add(txtProcModel, gbc_txtProcModel);
					txtProcModel.setColumns(10);
				}
				{
					txtProcArch = new JTextField();
					GridBagConstraints gbc_txtProcarch = new GridBagConstraints();
					gbc_txtProcarch.insets = new Insets(5, 0, 5, 0);
					gbc_txtProcarch.fill = GridBagConstraints.BOTH;
					gbc_txtProcarch.gridx = 3;
					gbc_txtProcarch.gridy = 1;
					extraInfoTab.add(txtProcArch, gbc_txtProcarch);
					txtProcArch.setColumns(10);
				}
				{
					JLabel lblMoreInfo = new JLabel("More Info:");
					GridBagConstraints gbc_lblMoreInfo = new GridBagConstraints();
					gbc_lblMoreInfo.anchor = GridBagConstraints.EAST;
					gbc_lblMoreInfo.insets = new Insets(5, 0, 5, 5);
					gbc_lblMoreInfo.gridx = 0;
					gbc_lblMoreInfo.gridy = 2;
					extraInfoTab.add(lblMoreInfo, gbc_lblMoreInfo);
				}
				{
					txtMoreCPUInfo = new JTextField();
					GridBagConstraints gbc_txtMoreinfo = new GridBagConstraints();
					gbc_txtMoreinfo.gridwidth = 2;
					gbc_txtMoreinfo.insets = new Insets(5, 0, 5, 5);
					gbc_txtMoreinfo.fill = GridBagConstraints.BOTH;
					gbc_txtMoreinfo.gridx = 1;
					gbc_txtMoreinfo.gridy = 2;
					extraInfoTab.add(txtMoreCPUInfo, gbc_txtMoreinfo);
					txtMoreCPUInfo.setColumns(10);
				}
				{
					JButton btnWebsite = new JButton("Open");
					btnWebsite.setFont(new Font("Dialog", Font.ITALIC, 12));
					btnWebsite.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (txtMoreCPUInfo.getText() != null && !txtMoreCPUInfo.getText().isEmpty()) {
								Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
								if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE) ) {
									try {
										desktop.browse(new URI(txtMoreCPUInfo.getText()));
									} catch (Exception ex) {
										ex.printStackTrace();
									}
								}
							}
						}
					});
					GridBagConstraints gbc_btnWebsite = new GridBagConstraints();
					gbc_btnWebsite.anchor = GridBagConstraints.WEST;
					gbc_btnWebsite.insets = new Insets(5, 0, 5, 0);
					gbc_btnWebsite.gridx = 3;
					gbc_btnWebsite.gridy = 2;
					extraInfoTab.add(btnWebsite, gbc_btnWebsite);
				}
				{
					JLabel lblNodes = new JLabel("Nodes:");
					GridBagConstraints gbc_lblNodes = new GridBagConstraints();
					gbc_lblNodes.anchor = GridBagConstraints.EAST;
					gbc_lblNodes.insets = new Insets(5, 0, 5, 5);
					gbc_lblNodes.gridx = 0;
					gbc_lblNodes.gridy = 4;
					extraInfoTab.add(lblNodes, gbc_lblNodes);
				}
				{
					txtNodes = new JTextField();
					GridBagConstraints gbc_txtNodes = new GridBagConstraints();
					gbc_txtNodes.insets = new Insets(5, 0, 5, 5);
					gbc_txtNodes.fill = GridBagConstraints.BOTH;
					gbc_txtNodes.gridx = 1;
					gbc_txtNodes.gridy = 4;
					extraInfoTab.add(txtNodes, gbc_txtNodes);
					txtNodes.setColumns(10);
				}
				{
					JLabel lblCores = new JLabel("Total Number of Cores:");
					GridBagConstraints gbc_lblCores = new GridBagConstraints();
					gbc_lblCores.anchor = GridBagConstraints.EAST;
					gbc_lblCores.insets = new Insets(5, 0, 5, 5);
					gbc_lblCores.gridx = 2;
					gbc_lblCores.gridy = 4;
					extraInfoTab.add(lblCores, gbc_lblCores);
				}
				{
					txtTotalCores = new JTextField();
					GridBagConstraints gbc_txtCores = new GridBagConstraints();
					gbc_txtCores.insets = new Insets(5, 0, 5, 0);
					gbc_txtCores.fill = GridBagConstraints.BOTH;
					gbc_txtCores.gridx = 3;
					gbc_txtCores.gridy = 4;
					extraInfoTab.add(txtTotalCores, gbc_txtCores);
					txtTotalCores.setColumns(5);
				}
				{
					JLabel lblProcPerNode = new JLabel("Procs per Node:");
					GridBagConstraints gbc_lblProcPerNode = new GridBagConstraints();
					gbc_lblProcPerNode.anchor = GridBagConstraints.EAST;
					gbc_lblProcPerNode.insets = new Insets(5, 0, 5, 5);
					gbc_lblProcPerNode.gridx = 0;
					gbc_lblProcPerNode.gridy = 5;
					extraInfoTab.add(lblProcPerNode, gbc_lblProcPerNode);
				}
				{
					txtProcPerNode = new JTextField();
					GridBagConstraints gbc_txtProcPerNode = new GridBagConstraints();
					gbc_txtProcPerNode.insets = new Insets(5, 0, 5, 5);
					gbc_txtProcPerNode.fill = GridBagConstraints.BOTH;
					gbc_txtProcPerNode.gridx = 1;
					gbc_txtProcPerNode.gridy = 5;
					extraInfoTab.add(txtProcPerNode, gbc_txtProcPerNode);
					txtProcPerNode.setColumns(5);
				}
				{
					JLabel lblCoresPerChip = new JLabel("Cores per Socket:");
					GridBagConstraints gbc_lblCoresPerChip = new GridBagConstraints();
					gbc_lblCoresPerChip.anchor = GridBagConstraints.EAST;
					gbc_lblCoresPerChip.insets = new Insets(5, 0, 5, 5);
					gbc_lblCoresPerChip.gridx = 2;
					gbc_lblCoresPerChip.gridy = 5;
					extraInfoTab.add(lblCoresPerChip, gbc_lblCoresPerChip);
				}
				{
					txtCoresPerSocket = new JTextField();
					GridBagConstraints gbc_txtCoresPerChip = new GridBagConstraints();
					gbc_txtCoresPerChip.fill = GridBagConstraints.BOTH;
					gbc_txtCoresPerChip.insets = new Insets(5, 0, 5, 0);
					gbc_txtCoresPerChip.gridx = 3;
					gbc_txtCoresPerChip.gridy = 5;
					extraInfoTab.add(txtCoresPerSocket, gbc_txtCoresPerChip);
					txtCoresPerSocket.setColumns(5);
				}
				{
					JLabel lblPeakPerf = new JLabel("Peak Perf (TFlop/s):");
					GridBagConstraints gbc_lblPeakPerf = new GridBagConstraints();
					gbc_lblPeakPerf.anchor = GridBagConstraints.EAST;
					gbc_lblPeakPerf.insets = new Insets(5, 0, 5, 5);
					gbc_lblPeakPerf.gridx = 0;
					gbc_lblPeakPerf.gridy = 7;
					extraInfoTab.add(lblPeakPerf, gbc_lblPeakPerf);
				}
				{
					txtPeakPerf = new JTextField();
					GridBagConstraints gbc_txtPeakPerf = new GridBagConstraints();
					gbc_txtPeakPerf.insets = new Insets(5, 0, 5, 5);
					gbc_txtPeakPerf.fill = GridBagConstraints.HORIZONTAL;
					gbc_txtPeakPerf.gridx = 1;
					gbc_txtPeakPerf.gridy = 7;
					extraInfoTab.add(txtPeakPerf, gbc_txtPeakPerf);
					txtPeakPerf.setColumns(10);
				}
				{
					JLabel lblPeakPerfPerCore = new JLabel("Peak Perf per Core (GFlop/s):");
					GridBagConstraints gbc_lblPeakPerfPerCore = new GridBagConstraints();
					gbc_lblPeakPerfPerCore.anchor = GridBagConstraints.EAST;
					gbc_lblPeakPerfPerCore.insets = new Insets(5, 0, 5, 5);
					gbc_lblPeakPerfPerCore.gridx = 2;
					gbc_lblPeakPerfPerCore.gridy = 7;
					extraInfoTab.add(lblPeakPerfPerCore, gbc_lblPeakPerfPerCore);
				}
				{
					txtPeakPerfPerCore = new JTextField();
					GridBagConstraints gbc_txtPeakPerfPerCore = new GridBagConstraints();
					gbc_txtPeakPerfPerCore.insets = new Insets(5, 0, 5, 0);
					gbc_txtPeakPerfPerCore.fill = GridBagConstraints.HORIZONTAL;
					gbc_txtPeakPerfPerCore.gridx = 3;
					gbc_txtPeakPerfPerCore.gridy = 7;
					extraInfoTab.add(txtPeakPerfPerCore, gbc_txtPeakPerfPerCore);
					txtPeakPerfPerCore.setColumns(10);
				}
				{
					JLabel lblMaxCpuFrequency = new JLabel("Max CPU Freq (MHz):");
					GridBagConstraints gbc_lblMaxCpuFrequency = new GridBagConstraints();
					gbc_lblMaxCpuFrequency.anchor = GridBagConstraints.EAST;
					gbc_lblMaxCpuFrequency.insets = new Insets(5, 0, 5, 5);
					gbc_lblMaxCpuFrequency.gridx = 0;
					gbc_lblMaxCpuFrequency.gridy = 8;
					extraInfoTab.add(lblMaxCpuFrequency, gbc_lblMaxCpuFrequency);
				}
				{
					txtMaxCpuFreq = new JTextField();
					GridBagConstraints gbc_txtMaxCpuFreq = new GridBagConstraints();
					gbc_txtMaxCpuFreq.insets = new Insets(5, 0, 5, 5);
					gbc_txtMaxCpuFreq.fill = GridBagConstraints.BOTH;
					gbc_txtMaxCpuFreq.gridx = 1;
					gbc_txtMaxCpuFreq.gridy = 8;
					extraInfoTab.add(txtMaxCpuFreq, gbc_txtMaxCpuFreq);
					txtMaxCpuFreq.setColumns(10);
				}
				{
					JLabel lblMemPerCore = new JLabel("Memory per Core (GByte):");
					GridBagConstraints gbc_lblMemPerCore = new GridBagConstraints();
					gbc_lblMemPerCore.anchor = GridBagConstraints.EAST;
					gbc_lblMemPerCore.insets = new Insets(5, 0, 5, 5);
					gbc_lblMemPerCore.gridx = 2;
					gbc_lblMemPerCore.gridy = 8;
					extraInfoTab.add(lblMemPerCore, gbc_lblMemPerCore);
				}
				{
					txtMemPerCore = new JTextField();
					GridBagConstraints gbc_txtMemPerCore = new GridBagConstraints();
					gbc_txtMemPerCore.insets = new Insets(5, 0, 5, 0);
					gbc_txtMemPerCore.fill = GridBagConstraints.HORIZONTAL;
					gbc_txtMemPerCore.gridx = 3;
					gbc_txtMemPerCore.gridy = 8;
					extraInfoTab.add(txtMemPerCore, gbc_txtMemPerCore);
					txtMemPerCore.setColumns(10);
				}
				{
					JLabel lblBandwidth = new JLabel("Memory Bandwidth (GB/s):");
					GridBagConstraints gbc_lblBandwidth = new GridBagConstraints();
					gbc_lblBandwidth.anchor = GridBagConstraints.EAST;
					gbc_lblBandwidth.insets = new Insets(5, 0, 5, 5);
					gbc_lblBandwidth.gridx = 0;
					gbc_lblBandwidth.gridy = 9;
					extraInfoTab.add(lblBandwidth, gbc_lblBandwidth);
				}
				{
					txtBandwidth = new JTextField();
					GridBagConstraints gbc_txtBandwidth = new GridBagConstraints();
					gbc_txtBandwidth.insets = new Insets(5, 0, 5, 5);
					gbc_txtBandwidth.fill = GridBagConstraints.BOTH;
					gbc_txtBandwidth.gridx = 1;
					gbc_txtBandwidth.gridy = 9;
					extraInfoTab.add(txtBandwidth, gbc_txtBandwidth);
					txtBandwidth.setColumns(10);
				}
				{
					JLabel lblMemoryChannels = new JLabel("Memory Channels:");
					GridBagConstraints gbc_lblMemoryChannels = new GridBagConstraints();
					gbc_lblMemoryChannels.anchor = GridBagConstraints.EAST;
					gbc_lblMemoryChannels.insets = new Insets(0, 0, 5, 5);
					gbc_lblMemoryChannels.gridx = 2;
					gbc_lblMemoryChannels.gridy = 9;
					extraInfoTab.add(lblMemoryChannels, gbc_lblMemoryChannels);
				}
				{
					txtMemoryChannels = new JTextField();
					GridBagConstraints gbc_txtMemoryChannels = new GridBagConstraints();
					gbc_txtMemoryChannels.insets = new Insets(5, 0, 5, 0);
					gbc_txtMemoryChannels.fill = GridBagConstraints.BOTH;
					gbc_txtMemoryChannels.gridx = 3;
					gbc_txtMemoryChannels.gridy = 9;
					extraInfoTab.add(txtMemoryChannels, gbc_txtMemoryChannels);
					txtMemoryChannels.setColumns(10);
				}
				{
					JLabel lblNetworkTechnology = new JLabel("Network Technology:");
					GridBagConstraints gbc_lblNetworkTechnology = new GridBagConstraints();
					gbc_lblNetworkTechnology.anchor = GridBagConstraints.EAST;
					gbc_lblNetworkTechnology.insets = new Insets(5, 0, 5, 5);
					gbc_lblNetworkTechnology.gridx = 0;
					gbc_lblNetworkTechnology.gridy = 11;
					extraInfoTab.add(lblNetworkTechnology, gbc_lblNetworkTechnology);
				}
				{
					txtNetTech = new JTextField();
					GridBagConstraints gbc_txtNetworkTechnology = new GridBagConstraints();
					gbc_txtNetworkTechnology.insets = new Insets(5, 0, 5, 5);
					gbc_txtNetworkTechnology.fill = GridBagConstraints.BOTH;
					gbc_txtNetworkTechnology.gridx = 1;
					gbc_txtNetworkTechnology.gridy = 11;
					extraInfoTab.add(txtNetTech, gbc_txtNetworkTechnology);
					txtNetTech.setColumns(10);
				}
				{
					JLabel lblTopology = new JLabel("Topology:");
					GridBagConstraints gbc_lblTopology = new GridBagConstraints();
					gbc_lblTopology.anchor = GridBagConstraints.EAST;
					gbc_lblTopology.insets = new Insets(5, 0, 5, 5);
					gbc_lblTopology.gridx = 2;
					gbc_lblTopology.gridy = 11;
					extraInfoTab.add(lblTopology, gbc_lblTopology);
				}
				{
					txtTopology = new JTextField();
					GridBagConstraints gbc_txtTopology = new GridBagConstraints();
					gbc_txtTopology.insets = new Insets(5, 0, 5, 0);
					gbc_txtTopology.fill = GridBagConstraints.BOTH;
					gbc_txtTopology.gridx = 3;
					gbc_txtTopology.gridy = 11;
					extraInfoTab.add(txtTopology, gbc_txtTopology);
					txtTopology.setColumns(10);
				}
				{
					JLabel lblFileSystem = new JLabel("File System:");
					GridBagConstraints gbc_lblFileSystem = new GridBagConstraints();
					gbc_lblFileSystem.anchor = GridBagConstraints.EAST;
					gbc_lblFileSystem.insets = new Insets(5, 0, 0, 5);
					gbc_lblFileSystem.gridx = 0;
					gbc_lblFileSystem.gridy = 12;
					extraInfoTab.add(lblFileSystem, gbc_lblFileSystem);
				}
				{
					txtFileSystem = new JTextField();
					GridBagConstraints gbc_txtFilesystem = new GridBagConstraints();
					gbc_txtFilesystem.insets = new Insets(5, 0, 0, 5);
					gbc_txtFilesystem.fill = GridBagConstraints.HORIZONTAL;
					gbc_txtFilesystem.gridx = 1;
					gbc_txtFilesystem.gridy = 12;
					extraInfoTab.add(txtFileSystem, gbc_txtFilesystem);
					txtFileSystem.setColumns(10);
				}
				middlePanel.setEnabledAt(1, false);
			}
		}
		{
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Close");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPanel.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		{
			JPanel topPanel = new JPanel();
			topPanel.setBorder(new EmptyBorder(10, 5, 10, 10));
			getContentPane().add(topPanel, BorderLayout.NORTH);
			topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
			{
				JLabel lblHPCSystem = new JLabel("HPC System: ");
				lblHPCSystem.setHorizontalAlignment(SwingConstants.LEFT);
				topPanel.add(lblHPCSystem);

			}
			{
				hpcSystemComboBox = new JComboBox(hpcSystemsInDB.keySet().toArray(new String[hpcSystemsInDB.size()]));
				hpcSystemComboBox.setMaximumRowCount(12);
				hpcSystemComboBox.setSelectedItem("");
				topPanel.add(hpcSystemComboBox);
				hpcSystemComboBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						JComboBox cb = (JComboBox) e.getSource();
						String hpcSystemName = (String) cb.getSelectedItem();

						System.out.println("HPCSystemComboBox: action performed -> " + hpcSystemName);

						// User selects an existing application - initialize the fields
						if (hpcSystemName != null && hpcSystemName.length() > 0 && hpcSystemsInDB.containsKey(hpcSystemName)) {
							HPCSystem hpcSystem = hpcSystemsInDB.get(hpcSystemName);
							txtOrganization.setText(hpcSystem.getOrganisation());
							txtWebsite.setText(hpcSystem.getWebsite());
							comboBatchScheduler.setSelectedItem(hpcSystem.getBatchSystem());

							// Fill the extra info tab
							txtNodes.setText( (hpcSystem.getNodes() != null) ? hpcSystem.getNodes().toString() : "");
							txtProcPerNode.setText( (hpcSystem.getProcessorsPerNode() != null) ? hpcSystem.getProcessorsPerNode().toString() : "");
							txtPeakPerf.setText( (hpcSystem.getSystemPeakPerformance() != null) ? hpcSystem.getSystemPeakPerformance().toString() : "");
							txtMemPerCore.setText( (hpcSystem.getMemoryPerCore() != null) ? hpcSystem.getMemoryPerCore().toString() : "");
							txtNetTech.setText(hpcSystem.getNetTechnology());
							txtTopology.setText(hpcSystem.getNetTopology());
							txtFileSystem.setText(hpcSystem.getFileSystem());
							txtTotalCores.setText( (hpcSystem.getTotalCores() != null ) ? hpcSystem.getTotalCores().toString() : "");

							if(hpcSystem.getHPCSystems_CPU() != null) {
								HPCSystem_CPU extraCPUInfo = hpcSystem.getHPCSystems_CPU();
								txtProcType.setText(extraCPUInfo.getProcessorType());
								txtProcModel.setText(extraCPUInfo.getModel());
								txtProcArch.setText(extraCPUInfo.getMicroarchitecture());

								txtMaxCpuFreq.setText( (extraCPUInfo.getPeakFrequencyPerCore() != null) ? extraCPUInfo.getPeakFrequencyPerCore().toString() : "");
								txtCoresPerSocket.setText( (extraCPUInfo.getCoresPerSocket() != null) ? extraCPUInfo.getCoresPerSocket().toString() : "");
								txtPeakPerfPerCore.setText( (extraCPUInfo.getPeakPerformancePerCore() != null) ? extraCPUInfo.getPeakPerformancePerCore().toString() : "");
								txtBandwidth.setText( (extraCPUInfo.getMemoryBandwidth() != null) ? extraCPUInfo.getMemoryBandwidth().toString() : "");
								txtMemoryChannels.setText( (extraCPUInfo.getMemoryChannels() != null) ? extraCPUInfo.getMemoryChannels().toString() : "");
								txtMoreCPUInfo.setText(extraCPUInfo.getMoreInfo());
							} else {
								txtProcType.setText("");
								txtProcModel.setText("");
								txtProcArch.setText("");
								txtMaxCpuFreq.setText("");
								txtCoresPerSocket.setText("");
								txtPeakPerfPerCore.setText("");
								txtBandwidth.setText("");
								txtMemoryChannels.setText("");
								txtMoreCPUInfo.setText("");
							}

							middlePanel.setEnabledAt(1, true);

						}
						else if ( hpcSystemName == null   ||   hpcSystemName.length() == 0 ){
							System.out.println("HPCSystemComboBox: clearing all fields...");

							txtOrganization.setText("");
							txtWebsite.setText("");
							comboBatchScheduler.setSelectedItem("-");
							middlePanel.setSelectedIndex(0);
							middlePanel.setEnabledAt(1, false);

							// Extra Information tab
							txtNodes.setText("");
							txtProcPerNode.setText("");
							txtPeakPerf.setText("");
							txtMemPerCore.setText("");
							txtNetTech.setText("");
							txtTopology.setText("");
							txtFileSystem.setText("");
							txtTotalCores.setText("");
							txtProcType.setText("");
							txtProcModel.setText("");
							txtProcArch.setText("");
							txtMaxCpuFreq.setText("");
							txtCoresPerSocket.setText("");
							txtPeakPerfPerCore.setText("");
							txtBandwidth.setText("");
							txtMemoryChannels.setText("");
							txtMoreCPUInfo.setText("");
						}
					}
				});
				hpcSystemComboBox.setEditable(true);

			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(30);
				topPanel.add(horizontalStrut);
			}
			{
				JButton btnAddselected = new JButton("");
				topPanel.add(btnAddselected);
				btnAddselected.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String hpcSystemName = (String) hpcSystemComboBox.getSelectedItem();

						// Adding the new configuration was successful?
						if (addNewHPCSystem(hpcSystemName)) {
							initializeHPCSystemsList();
							DefaultComboBoxModel model = new DefaultComboBoxModel( hpcSystemsInDB.keySet().toArray(new String[hpcSystemsInDB.size()]) );
							hpcSystemComboBox.setModel( model );
							hpcSystemComboBox.setSelectedItem(hpcSystemName);

							JOptionPane.showMessageDialog(getContentPane().getParent(), "The following HPC system configuration was stored in the database: " + hpcSystemName, "HPC System Configuration Stored",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				btnAddselected.setIcon(new ImageIcon(EditHPCSystems.class.getResource("/icons/save_edit.gif")));
			}
			{
				JButton btnDeleteSelected = new JButton("");
				topPanel.add(btnDeleteSelected);
				btnDeleteSelected.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String hpcSystemName = (String) hpcSystemComboBox.getSelectedItem();

						int rVal = JOptionPane.showConfirmDialog(getContentPane().getParent(), "Are you sure you want to delete the configuration for '" + hpcSystemName + "' from the database?", "Delete HPC System Config?", JOptionPane.YES_NO_OPTION);
						if (rVal == JOptionPane.NO_OPTION)
							return;

						if (deleteSelectedHPCSystem(hpcSystemName)) {
							initializeHPCSystemsList();
							DefaultComboBoxModel model = new DefaultComboBoxModel( hpcSystemsInDB.keySet().toArray(new String[hpcSystemsInDB.size()]) );
							hpcSystemComboBox.setModel( model );
							hpcSystemComboBox.setSelectedItem("");
										
							JOptionPane.showMessageDialog(getContentPane().getParent(), "The following HPC system configuration was deleted from the database: " + hpcSystemName, "HPC System Configuration Removed",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				btnDeleteSelected.setIcon(new ImageIcon(EditHPCSystems.class.getResource("/icons/delete_obj.gif")));
			}
		}
	}

	private void initializeHPCSystemsList()
	{
		List<HPCSystem> availHPCSystems = new ArrayList<HPCSystem>();
		try {
			pathway.data.persistence.PathwayPersistentManager.instance().getSession().flush();
			availHPCSystems = HPCSystem.queryHPCSystem(null, null);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error while querying the available HPC systems from the internal database:\n" + e.getLocalizedMessage(), "Error querying the internal database",
					JOptionPane.ERROR_MESSAGE);
			System.exit(ABORT);
		}

		hpcSystemsInDB = new HashMap<String, HPCSystem>();
		HPCSystem emptySystem = new HPCSystem();
		hpcSystemsInDB.put("", emptySystem);

		availBatchSchedulers = new ArrayList<String>();
		availBatchSchedulers.add("-");
		availBatchSchedulers.addAll(pathwayBatchSchedulers );
		for (HPCSystem hpcSystem : availHPCSystems) {
			hpcSystemsInDB.put(hpcSystem.getName(), hpcSystem);
			availBatchSchedulers.add(hpcSystem.getBatchSystem());
		}
	}

	protected boolean deleteSelectedHPCSystem(String hpcSystemName) {
		boolean result = false;
		if (!hpcSystemName.isEmpty() && hpcSystemsInDB.containsKey(hpcSystemName)) {
			HPCSystem hpcSystem = hpcSystemsInDB.get(hpcSystemName);

			try {
				hpcSystem.refresh();
				hpcSystemsInDB.remove(hpcSystemName);
				result = hpcSystem.deleteAndDissociate();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(getContentPane().getParent(), "There was an error while deleting the selected HPC system: " + hpcSystemName +"\n" + e.getLocalizedMessage(), "Error deleting HPC system",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                ExecutionParameters.updateComboBoxes();
            }
        });
		return result;
	}

	protected boolean addNewHPCSystem(String hpcSystemName)
	{
		if (hpcSystemName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please specify at least hostname, login host and port and username...", "Incomplete configuration",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		PersistentTransaction t;
		try {
			t = PathwayPersistentManager.instance().getSession().beginTransaction();

			try {
				// Check if we should update an existing application object or create a new one
				HPCSystem hpcSystem = null;

				if (hpcSystemsInDB.containsKey(hpcSystemName)) {
					hpcSystem = hpcSystemsInDB.get(hpcSystemName);
					hpcSystem.refresh();
				}
				else {
					hpcSystem = HPCSystem.createHPCSystem();
				}

				HPCSystem_CPU extraCPUInfo = null;
				if (hpcSystem.getHPCSystems_CPU() != null) {
					extraCPUInfo = hpcSystem.getHPCSystems_CPU();
					extraCPUInfo.refresh();
				} else {
					extraCPUInfo = HPCSystem_CPU.createHPCSystem_CPU();
				}

				hpcSystem.setName(hpcSystemName);
				hpcSystem.setOrganisation(txtOrganization.getText());
				hpcSystem.setWebsite(txtWebsite.getText());
				hpcSystem.setBatchSystem(comboBatchScheduler.getSelectedItem().toString());

				// Extra Information tab
				if (!txtTotalCores.getText().isEmpty()) 		hpcSystem.setTotalCores(Integer.valueOf(txtTotalCores.getText()));
				if (!txtNodes.getText().isEmpty()) 				hpcSystem.setNodes(Integer.valueOf(txtNodes.getText()));
				if (!txtProcPerNode.getText().isEmpty()) 	hpcSystem.setProcessorsPerNode(Integer.valueOf(txtProcPerNode.getText()));
				if (!txtPeakPerf.getText().isEmpty()) 			hpcSystem.setSystemPeakPerformance(Float.valueOf(txtPeakPerf.getText()));
				if (!txtMemPerCore.getText().isEmpty()) 	hpcSystem.setMemoryPerCore(Float.valueOf(txtMemPerCore.getText()));
				if (!txtFileSystem.getText().isEmpty()) 		hpcSystem.setFileSystem(txtFileSystem.getText());
				if (!txtNetTech.getText().isEmpty()) 			hpcSystem.setNetTechnology(txtNetTech.getText());
				if (!txtTopology.getText().isEmpty()) 			hpcSystem.setNetTopology(txtTopology.getText());

				if (!txtProcType.getText().isEmpty())					extraCPUInfo.setProcessorType(txtProcType.getText()); else extraCPUInfo.setProcessorType("Unknown");
				if (!txtProcModel.getText().isEmpty())				extraCPUInfo.setModel(txtProcModel.getText());
				if (!txtProcArch.getText().isEmpty())					extraCPUInfo.setMicroarchitecture(txtProcArch.getText());

				if (!txtCoresPerSocket.getText().isEmpty())		extraCPUInfo.setCoresPerSocket(Integer.valueOf(txtCoresPerSocket.getText()));
				if (!txtMaxCpuFreq.getText().isEmpty())			extraCPUInfo.setPeakFrequencyPerCore(Float.valueOf(txtMaxCpuFreq.getText()));
				if (!txtPeakPerfPerCore.getText().isEmpty()) 	extraCPUInfo.setPeakPerformancePerCore(Float.valueOf(txtPeakPerfPerCore.getText()));

				if (!txtBandwidth.getText().isEmpty()) 				extraCPUInfo.setMemoryBandwidth(Float.valueOf(txtBandwidth.getText()));
				if (!txtMemoryChannels.getText().isEmpty()) 	extraCPUInfo.setMemoryChannels(Integer.valueOf(txtMemoryChannels.getText()));
				if (!txtMoreCPUInfo.getText().isEmpty())			extraCPUInfo.setMoreInfo(txtMoreCPUInfo.getText());

				hpcSystem.setHPCSystems_CPU(extraCPUInfo);
				extraCPUInfo.setName(hpcSystem);

				// Update the properties of the persistent object
				hpcSystem.save();

				t.commit();
			} catch (Exception e) {
				t.rollback();
				System.err.println("Error while persisting HPC configuration. Exception: ");
				e.printStackTrace();
				return false;
			}
		} catch (PersistentException e1) {
			System.err.println("Error while persisting HPC configuration. Exception: ");
			e1.printStackTrace();
			return false;
		}

        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                ExecutionParameters.updateComboBoxes();
            }
        });
		return true;
	}


    /**
     * Opens the PTP edit dialog for a connection. If the connection does not exist yet, it is created. Returns a null reference, if the user cancels the
     * dialog.
     */
    public static @Nullable IRemoteConnection editConnection(@NonNull String name) throws RemoteConnectionException {
        IRemoteServices service = RemoteServices.getRemoteServices(ConnectionManager.REMOTE_SERVICE_ID, null);
        IRemoteUIServices uiService = RemoteUIServices.getRemoteUIServices(service);
        IRemoteUIConnectionManager mgr = uiService.getUIConnectionManager();
        IRemoteConnection connection = service.getConnectionManager().getConnection(name);
        IRemoteUIConnectionWizard wizard = mgr.getConnectionWizard(PAThWayPlugin.getShell());
        IRemoteConnectionWorkingCopy workingCopy;

        if( connection == null ) {
            wizard.setConnectionName(name);
            workingCopy = wizard.open();
        }
        else {
            wizard.setConnection(connection.getWorkingCopy());
            workingCopy = wizard.open();
        }

        return workingCopy == null ? null : workingCopy.save();
    }


	private static final long serialVersionUID = -8306541936120811744L;
    private static final List<String> pathwayBatchSchedulers = new ArrayList<String>(Arrays.asList(BatchSystemManager.HPC_SCHEDULER_MPICH, BatchSystemManager.HPC_SCHEDULER_SLURM, BatchSystemManager.HPC_SCHEDULER_SLURM_LRZ, BatchSystemManager.HPC_SCHEDULER_LOAD_LEVELER, BatchSystemManager.HPC_SCHEDULER_LOAD_LEVELER_MPICH, BatchSystemManager.HPC_SCHEDULER_PLATFORM_LSF));


    @NonNullByDefault
    private static class EditConnectionRunnable implements Runnable {
        public EditConnectionRunnable(String connectionName) {
            this.connectionName = connectionName;
        }


        @Override
        public void run() {
            try {
                editConnection(connectionName);
            }
            catch( RemoteConnectionException ex ) {
                ex.printStackTrace();
                UIUtils.showErrorMessage("Unable to create the connection.", ex);
            }
        }


        private final String connectionName;
    }
}
