<?xml version="1.0" encoding="UTF-8"?>
<Experiment xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.lrr.in.tum.de/Periscope" xsi:schemaLocation="http://www.lrr.in.tum.de/Periscope psc_properties.xsd ">

  <date>2012-05-31</date>
  <time>17:32:24</time>
  <numProcs>4</numProcs>
  <numThreads>4</numThreads>
  <dir>/home/petkovve/workspaces/LRR/Tests/NPB/NPB3.3-MZ-MPI/bin.periscope</dir>
  <sir>./bt-mz_W.4.sir</sir>

  <property cluster="false" ID="7-511" >
	<name>Excessive MPI communication time</name>
	<context FileID="11" FileName="exch_qbc.f" RFL="121" Config="4x4" Region="CALL_REGION" RegionId="11-734" >
		<execObj process="0" thread="0"/>
	</context>
	<severity>16.52</severity>
	<confidence>1.00</confidence>
	<addInfo>
		<CallTime>1088778</CallTime>
		<PhaseTime>6590359</PhaseTime>
	</addInfo>
  </property>
  <property cluster="false" ID="7-518" >
	<name>Excessive MPI time in receive due to late sender</name>
	<context FileID="11" FileName="exch_qbc.f" RFL="121" Config="4x4" Region="CALL_REGION" RegionId="11-734" >
		<execObj process="0" thread="0"/>
	</context>
	<severity>13.40</severity>
	<confidence>1.00</confidence>
	<addInfo>
		<CallTime>1088778</CallTime>
		<LateTime>882780</LateTime>
		<PhaseTime>6590359</PhaseTime>
	</addInfo>
  </property>
  <property cluster="false" ID="7-511" >
	<name>Excessive MPI communication time</name>
	<context FileID="11" FileName="exch_qbc.f" RFL="121" Config="4x4" Region="CALL_REGION" RegionId="11-734" >
		<execObj process="1" thread="0"/>
	</context>
	<severity>8.46</severity>
	<confidence>1.00</confidence>
	<addInfo>
		<CallTime>557665</CallTime>
		<PhaseTime>6589987</PhaseTime>
	</addInfo>
  </property>
  <property cluster="false" ID="7-518" >
	<name>Excessive MPI time in receive due to late sender</name>
	<context FileID="11" FileName="exch_qbc.f" RFL="121" Config="4x4" Region="CALL_REGION" RegionId="11-734" >
		<execObj process="1" thread="0"/>
	</context>
	<severity>5.03</severity>
	<confidence>1.00</confidence>
	<addInfo>
		<CallTime>557665</CallTime>
		<LateTime>331651</LateTime>
		<PhaseTime>6589987</PhaseTime>
	</addInfo>
  </property>
  <property cluster="false" ID="7-511" >
	<name>Excessive MPI communication time</name>
	<context FileID="11" FileName="exch_qbc.f" RFL="121" Config="4x4" Region="CALL_REGION" RegionId="11-734" >
		<execObj process="2" thread="0"/>
	</context>
	<severity>5.29</severity>
	<confidence>1.00</confidence>
	<addInfo>
		<CallTime>348933</CallTime>
		<PhaseTime>6590291</PhaseTime>
	</addInfo>
  </property>
  <property cluster="false" ID="7-511" >
	<name>Excessive MPI communication time</name>
	<context FileID="11" FileName="exch_qbc.f" RFL="121" Config="4x4" Region="CALL_REGION" RegionId="11-734" >
		<execObj process="3" thread="0"/>
	</context>
	<severity>10.12</severity>
	<confidence>1.00</confidence>
	<addInfo>
		<CallTime>667230</CallTime>
		<PhaseTime>6590135</PhaseTime>
	</addInfo>
  </property>
  <property cluster="false" ID="7-518" >
	<name>Excessive MPI time in receive due to late sender</name>
	<context FileID="11" FileName="exch_qbc.f" RFL="121" Config="4x4" Region="CALL_REGION" RegionId="11-734" >
		<execObj process="3" thread="0"/>
	</context>
	<severity>8.08</severity>
	<confidence>1.00</confidence>
	<addInfo>
		<CallTime>667230</CallTime>
		<LateTime>532262</LateTime>
		<PhaseTime>6590135</PhaseTime>
	</addInfo>
  </property>
</Experiment>
