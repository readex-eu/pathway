alter table "PUBLIC".HPCSystem_CPU drop constraint FKHPCSystem_734544;
alter table "PUBLIC".PscProperty drop constraint FK_RegionID;
alter table "PUBLIC".PscPropAddInfo drop constraint FKPscPropAdd609665;
alter table "PUBLIC".PscRegion drop constraint FK_ParentRegion;
alter table "PUBLIC".PscRegion drop constraint FKPscRegion646605;
alter table "PUBLIC".PscProperty drop constraint FK_ExpID;
alter table "PUBLIC".ExperimentConfig drop constraint FK_ExpConfig;
alter table "PUBLIC".ExperimentAddInfo drop constraint FKExperiment852816;
alter table "PUBLIC".Experiment drop constraint FK_HPCSystem;
alter table "PUBLIC".Experiment drop constraint FK_ToolID;
alter table "PUBLIC".Experiment drop constraint FKExperiment636198;
alter table "PUBLIC".Experiment drop constraint parent;
alter table "PUBLIC".Experiment drop constraint Notes;
drop table "PUBLIC".ExperimentConfig if exists;
drop table "PUBLIC".HPCSystem if exists;
drop table "PUBLIC".Tool if exists;
drop table "PUBLIC".PscProperty if exists;
drop table "PUBLIC".PscRegion if exists;
drop table "PUBLIC".HPCSystem_CPU if exists;
drop table "PUBLIC".PscPropAddInfo if exists;
drop table "PUBLIC".Application if exists;
drop table "PUBLIC".ExperimentAddInfo if exists;
drop table "PUBLIC".Experiment if exists;
drop table "PUBLIC".HistoricalNotes if exists;
create table "PUBLIC".ExperimentConfig (
  ExperimentID   char(32) not null, 
  ExperimentType varchar(50) default 'Single-Run Analysis' not null, 
  MpiProcs       int not null, 
  OmpThreads     int not null, 
  StartupFolder  varchar(255) not null, 
  CodeVersion    varchar(255) not null, 
  ExecCmd        varchar(255) not null, 
  StdOut         text not null, 
  StdErr         text not null, 
  CompileLog     text, 
  LoadedModules  text, 
  Environment    text, 
  Comment        text, 
  AnalysisType   varchar(10) default 'Profile' not null, 
  primary key (ExperimentID));
create table "PUBLIC".HPCSystem (
  Name                  varchar(255) not null, 
  Hostname              varchar(255) not null, 
  Organisation          varchar(50), 
  Website               varchar(50), 
  BatchSystem           varchar(50), 
  ConnHost              varchar(255) not null, 
  ConnPort              int not null, 
  ConnUser              varchar(255) not null, 
  ConnSSHKey            varchar(255), 
  TotalCores            int, 
  Nodes                 int, 
  ProcessorsPerNode     int, 
  SystemPeakPerformance float, 
  MemoryPerCore         float, 
  FileSystem            varchar(255), 
  NetTechnology         varchar(255), 
  NetTopology           varchar(50), 
  AvailableModules      text, 
  primary key (Name));
create table "PUBLIC".Tool (
  ID            int not null, 
  Name          varchar(50) not null, 
  Version       varchar_ignorecase(10) not null, 
  Profiling     boolean default 'false' not null, 
  Tracing       boolean default 'false' not null, 
  InstrumentCMD text not null, 
  InstrSuffix   varchar(50) not null, 
  ProfileCMD    text not null, 
  ProfileArgs   text not null, 
  TraceCMD      text not null, 
  TraceArgs     text not null, 
  ReqModules    text not null, 
  ReqEnvVars    text not null, 
  Website       varchar(255) not null, 
  primary key (ID), 
  constraint "Name-Version-Constr" 
    unique (Name, Version));
create table "PUBLIC".PscProperty (
  ID            bigint generated by default as identity, 
  ExperimentID  char(32) not null, 
  RegionID      varchar(50), 
  Name          varchar(50), 
  Type          varchar(10) not null, 
  "Clustered"   boolean, 
  Configuration varchar(10), 
  Process       int, 
  Thread        int, 
  Severity      double, 
  Confidence    double, 
  FileID        int, 
  FileName      varchar(255), 
  RFL           int, 
  RegionType    varchar(255), 
  primary key (ID));
create table "PUBLIC".PscRegion (
  ID                varchar(50) not null, 
  Application       varchar(25) default 'default app', 
  Name              varchar(255), 
  Type              varchar(25), 
  SourceFile        varchar(255), 
  StartLine         int, 
  EndLine           int, 
  DataScope         varchar(20), 
  PscRegionParent   varchar(50), 
  ApplicationConfig varchar(25), 
  primary key (ID));
create table "PUBLIC".HPCSystem_CPU (
  Name                   varchar(255) not null, 
  ProcessorType          varchar(50), 
  Model                  varchar(50), 
  Microarchitecture      varchar(50), 
  CoresPerSocket         int, 
  PeakFrequencyPerCore   float, 
  PeakPerformancePerCore float, 
  L1Cache                int, 
  L2Cache                int, 
  L3Cache                int, 
  Process                int, 
  DieSize                int, 
  Transistors            bigint, 
  MemoryChannels         int, 
  MemoryBandwidth        float, 
  MoreInfo               varchar(255), 
  primary key (Name));
create table "PUBLIC".PscPropAddInfo (
  PscPropertyID bigint not null, 
  Name          varchar(255) not null, 
  Value         varchar(255), 
  primary key (PscPropertyID, 
  Name));
create table "PUBLIC".Application (
  Name               varchar(25) not null, 
  Config             varchar(25) default 'default' not null, 
  Executable         varchar(255) not null, 
  StartArgs          varchar(255), 
  CodeLocation       varchar(255), 
  ExecLocation       varchar(255), 
  ReqModules         text, 
  ReqEnvVars         text, 
  EclipseProject     varchar(255), 
  CurrentCodeVersion varchar(255), 
  primary key (Name, 
  Config));
create table "PUBLIC".ExperimentAddInfo (
  ExperimentID char(32) not null, 
  Name         varchar(255) not null, 
  Value        varchar(255), 
  primary key (ExperimentID, 
  Name));
create table "PUBLIC".Experiment (
  ID                char(32) not null, 
  ExpDate           datetime not null, 
  ToolID            int not null, 
  HPCSystem         varchar(255) not null, 
  UserName          varchar(255), 
  ResultsURI        varchar(255), 
  Application       varchar(25), 
  ApplicationConfig varchar(25), 
  Parent            char(32), 
  HistoricalNotesID int, 
  JobId             varchar(50), 
  JobState          varchar(25), 
  primary key (ID));
create table "PUBLIC".HistoricalNotes (
  ID       int generated by default as identity, 
  NoteDate date not null, 
  Notes    text, 
  primary key (ID));
alter table "PUBLIC".HPCSystem_CPU add constraint FKHPCSystem_734544 foreign key (Name) references "PUBLIC".HPCSystem (Name) on update Cascade on delete Cascade;
alter table "PUBLIC".PscProperty add constraint FK_RegionID foreign key (RegionID) references "PUBLIC".PscRegion (ID) on update Cascade on delete Set null;
alter table "PUBLIC".PscPropAddInfo add constraint FKPscPropAdd609665 foreign key (PscPropertyID) references "PUBLIC".PscProperty (ID) on update Cascade on delete Cascade;
alter table "PUBLIC".PscRegion add constraint FK_ParentRegion foreign key (PscRegionParent) references "PUBLIC".PscRegion (ID) on update Cascade on delete Set null;
alter table "PUBLIC".PscRegion add constraint FKPscRegion646605 foreign key (Application, ApplicationConfig) references "PUBLIC".Application (Name, Config) on update Cascade on delete Set null;
alter table "PUBLIC".PscProperty add constraint FK_ExpID foreign key (ExperimentID) references "PUBLIC".Experiment (ID) on update Cascade on delete Cascade;
alter table "PUBLIC".ExperimentConfig add constraint FK_ExpConfig foreign key (ExperimentID) references "PUBLIC".Experiment (ID) on update Cascade on delete Cascade;
alter table "PUBLIC".ExperimentAddInfo add constraint FKExperiment852816 foreign key (ExperimentID) references "PUBLIC".Experiment (ID) on update Cascade on delete Cascade;
alter table "PUBLIC".Experiment add constraint FK_HPCSystem foreign key (HPCSystem) references "PUBLIC".HPCSystem (Name) on update Cascade on delete Set null;
alter table "PUBLIC".Experiment add constraint FK_ToolID foreign key (ToolID) references "PUBLIC".Tool (ID) on update Cascade on delete Set null;
alter table "PUBLIC".Experiment add constraint FKExperiment636198 foreign key (Application, ApplicationConfig) references "PUBLIC".Application (Name, Config) on update Cascade on delete Set null;
alter table "PUBLIC".Experiment add constraint parent foreign key (Parent) references "PUBLIC".Experiment (ID);
alter table "PUBLIC".Experiment add constraint Notes foreign key (HistoricalNotesID) references "PUBLIC".HistoricalNotes (ID);