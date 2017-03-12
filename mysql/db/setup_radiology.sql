DROP TABLE IF EXISTS `radiologytype` ;

CREATE TABLE `radiologytype` (
  `RADC_ID_A` char(2) NOT NULL,
  `RADC_DESC` varchar(50) NOT NULL,
  PRIMARY KEY (`RADC_ID_A`)
) ENGINE=InnoDB DEFAULT CHARSET=latin2;

INSERT INTO `radiologytype` (`RADC_ID_A`, `RADC_DESC`) VALUES
	('CT', '1.CT scan'),
	('UL', '2.Ultrasound'),
	('XR', '3.Xrays');

DROP TABLE IF EXISTS `radiology` ;
CREATE TABLE `radiology` (
  `RAD_ID_A` varchar(10) NOT NULL,
  `RAD_DESC` varchar(100) NOT NULL,
  `RAD_RADC_ID_A` char(2) NOT NULL,
  PRIMARY KEY (`RAD_ID_A`),
  KEY `RAD_RADC_ID_A` (`RAD_RADC_ID_A`)
) ENGINE=InnoDB DEFAULT CHARSET=latin2;

INSERT INTO `radiology` (`RAD_ID_A`, `RAD_DESC`, `RAD_RADC_ID_A`) VALUES
	('01.01', '1.1. CT:Abdomen', 'CT'),
	('01.02', '1.2. CT:Abdominal Cavity', 'CT'),
	('01.03', '1.3. CT:Adipose', 'CT'),
	('01.04', '1.4. CT:Anabolism', 'CT'),
	('01.05', '1.5. CT:Anterior', 'CT'),
	('01.06', '1.6. CT:Cartilage', 'CT'),
	('01.07', '1.7. CT:Catabolism', 'CT'),
	('02.01', '2.1. US:Abdomen', 'UL'),
	('02.02', '2.2. US:Abdominal Cavity', 'UL'),
	('02.03', '2.3. US:Adipose', 'UL'),
	('02.04', '2.4. US:Anabolism', 'UL'),
	('02.05', '2.5. US:Anterior', 'UL'),
	('02.06', '2.6. US:Cartilage', 'UL'),
	('02.07', '2.7. US:Catabolism', 'UL'),
	('03.01', '3.1. XR:Abdomen', 'XR'),
	('03.02', '3.2. XR:Abdominal Cavity', 'XR'),
	('03.03', '3.3. XR:Adipose', 'XR'),
	('03.04', '3.4. XR:Anabolism', 'XR'),
	('03.05', '3.5. XR:Anterior', 'XR'),
	('03.06', '3.6. XR:Cartilage', 'XR'),
	('03.07', '3.7. XR:Catabolism', 'XR');



DROP TABLE IF EXISTS `radiologyexam` ;
CREATE TABLE `radiologyexam` (
  `RADEX_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RADEX_RAD_ID_A` varchar(10) NOT NULL,
  `RADEX_DATE` datetime NOT NULL,
  `RADEX_PAT_ID` int(11) NOT NULL,
  `CLN_HST` varchar(1000) DEFAULT 'NONE',
  `CMP` varchar(1000) DEFAULT 'NONE',
  `TCN` varchar(1000) DEFAULT 'NONE',
  `FND` varchar(1000) DEFAULT 'NONE',
  `IMP` varchar(1000) DEFAULT 'NONE',
  `RADIOLOGIST` varchar(50) DEFAULT 'NONE',
  PRIMARY KEY (`RADEX_ID`),
  KEY `RADEX_RAD_ID_A` (`RADEX_RAD_ID_A`),
  KEY `RADEX_PAT_ID` (`RADEX_PAT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=latin2;


DROP TABLE IF EXISTS `radiologyexamdicom` ;
CREATE TABLE `radiologyexamdicom` (
  `RADEXDCM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RADEXDCM_RADEX_ID` int(11) NOT NULL DEFAULT '0',
  `RADEXDCM_DM_FILE_ID` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`RADEXDCM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=latin2;


DROP TABLE IF EXISTS `radiologytable` ;
CREATE TABLE `radiologytable` (
  `RADTABLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DATE` varchar(50) NOT NULL,
  `PATIENT` varchar(50) NOT NULL,
  `SCAN` varchar(50) NOT NULL,
  `MAC_ADDRESS` varchar(50) NOT NULL,
  PRIMARY KEY (`RADTABLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin2;




INSERT INTO `menuitem` (`MNI_ID_A`, `MNI_BTN_LABEL`, `MNI_LABEL`, `MNI_TOOLTIP`, `MNI_SHORTCUT`, `MNI_SUBMENU`, `MNI_CLASS`, `MNI_IS_SUBMENU`, `MNI_POSITION`) VALUES
('radiology', 'angal.menu.btn.radiology', 'angal.menu.radiology', 'x', 'R', 'main', 'org.isf.radiology.gui.RadiologyExamBrowser', 'N', 10);




INSERT INTO `groupmenu` (`GM_UG_ID_A`, `GM_MNI_ID_A`, `GM_ACTIVE`) VALUES 
('admin', 'radiology', 'Y');