
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.18
-- Dumped by pg_dump version 9.1.18
-- Started on 2016-03-20 20:51:59 IST


CREATE DATABASE bnotonpdb
  WITH OWNER = bnotonpadm
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_IN'
       LC_CTYPE = 'en_IN'
       CONNECTION LIMIT = -1;


SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 170 (class 1259 OID 49829)
-- Dependencies: 5
-- Name: onp_audittable; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE onp_audittable (
    bank_id character varying(5) NOT NULL,
    batch_name character varying(32),
    tran_type character varying(3),
    ipaddress character varying(25),
    text text,
    r_cre_id character varying(20),
    r_cre_time timestamp without time zone
);


ALTER TABLE public.onp_audittable OWNER TO bnotonpadm;

--
-- TOC entry 163 (class 1259 OID 49777)
-- Dependencies: 5
-- Name: onp_bank_msg_format; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE onp_bank_msg_format (
    msgid integer NOT NULL,
    bankid character varying(5) NOT NULL,
    branchid character varying(30),
    msg_lang character varying(6),
    msg_channel_id character varying(9),
    messagetext character varying(250),
    del_flg character varying(1),
    r_cre_id character varying(30),
    r_cre_time timestamp without time zone,
    r_mod_id character varying(30),
    r_mod_time timestamp without time zone
);


ALTER TABLE public.onp_bank_msg_format OWNER TO bnotonpadm;

--
-- TOC entry 162 (class 1259 OID 49775)
-- Dependencies: 163 5
-- Name: onp_bank_msg_format_msgid_seq; Type: SEQUENCE; Schema: public; Owner: bnotonpadm
--

CREATE SEQUENCE onp_bank_msg_format_msgid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.onp_bank_msg_format_msgid_seq OWNER TO bnotonpadm;

--
-- TOC entry 2023 (class 0 OID 0)
-- Dependencies: 162
-- Name: onp_bank_msg_format_msgid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bnotonpadm
--

ALTER SEQUENCE onp_bank_msg_format_msgid_seq OWNED BY onp_bank_msg_format.msgid;


--
-- TOC entry 169 (class 1259 OID 49823)
-- Dependencies: 5
-- Name: onp_broadcast_msg; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE onp_broadcast_msg (
    msgid character varying(3),
    bankid character varying(5),
    entityname character varying(20),
    branchid character varying(30),
    channel_id character varying(5),
    mobilenumber numeric,
    notificationtext text,
    msgdelflag boolean,
    r_cre_time timestamp without time zone
);


ALTER TABLE public.onp_broadcast_msg OWNER TO bnotonpadm;

--
-- TOC entry 168 (class 1259 OID 49814)
-- Dependencies: 5
-- Name: onp_custom_message_format; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE onp_custom_message_format (
    msgid integer NOT NULL,
    bank_id character varying(5) NOT NULL,
    branch_id character varying(30),
    channel_id character varying(9),
    message_text text,
    msg_delivery_flg character varying(1),
    r_cre_id character varying(30),
    r_cre_time timestamp without time zone
);


ALTER TABLE public.onp_custom_message_format OWNER TO bnotonpadm;

--
-- TOC entry 167 (class 1259 OID 49812)
-- Dependencies: 168 5
-- Name: onp_custom_message_format_msgid_seq; Type: SEQUENCE; Schema: public; Owner: bnotonpadm
--

CREATE SEQUENCE onp_custom_message_format_msgid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.onp_custom_message_format_msgid_seq OWNER TO bnotonpadm;

--
-- TOC entry 2026 (class 0 OID 0)
-- Dependencies: 167
-- Name: onp_custom_message_format_msgid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bnotonpadm
--

ALTER SEQUENCE onp_custom_message_format_msgid_seq OWNED BY onp_custom_message_format.msgid;


--
-- TOC entry 182 (class 1259 OID 58910)
-- Dependencies: 1858 5
-- Name: onp_customer_reg; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE onp_customer_reg (
    bankid character varying(5) NOT NULL,
    branchid character varying(30),
    mobilenumber numeric NOT NULL,
    account_number character varying(20),
    cust_id character varying(20),
    cust_category character varying(10),
    mobileusageactivated character varying(1) DEFAULT 'f'::character varying,
    del_flg character varying(1),
    r_cre_id character varying(30),
    r_cre_time timestamp without time zone,
    r_mod_id character varying(30),
    r_mod_time timestamp without time zone,
    free_text1 character varying(64),
    free_text2 character varying(64),
    free_text3 character varying(64)
);


ALTER TABLE public.onp_customer_reg OWNER TO bnotonpadm;

--
-- TOC entry 165 (class 1259 OID 49788)
-- Dependencies: 5
-- Name: onp_dnd_mobile_reg; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE onp_dnd_mobile_reg (
    mobilenumber numeric
);


ALTER TABLE public.onp_dnd_mobile_reg OWNER TO bnotonpadm;

--
-- TOC entry 164 (class 1259 OID 49783)
-- Dependencies: 5
-- Name: onp_property_manager; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE onp_property_manager (
    property_name character varying(30) NOT NULL,
    property_value character varying(250) NOT NULL
);


ALTER TABLE public.onp_property_manager OWNER TO bnotonpadm;

--
-- TOC entry 166 (class 1259 OID 49794)
-- Dependencies: 5
-- Name: onp_user_profile; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE onp_user_profile (
    bank_id character varying(5) NOT NULL,
    corpid character varying(9),
    userid character varying(9) NOT NULL,
    pwd character varying(60),
    no_of_atmpts integer,
    saluation character varying(5),
    first_name character varying(32),
    middle_name character varying(32),
    last_name character varying(32),
    mobile_no_1 character varying(15),
    email_id_1 character varying(50),
    pwd_reset_date timestamp without time zone,
    pwd_exp_date timestamp without time zone,
    pwd_enable_flag character varying(1),
    last_login_date timestamp without time zone,
    last_logoff_date timestamp without time zone,
    del_flg character varying(1),
    r_cre_id character varying(20),
    r_cre_time timestamp without time zone,
    r_mod_id character varying(20),
    r_mod_time timestamp without time zone,
    free_text1 character varying(32),
    free_text2 character varying(32),
    free_text3 character varying(32),
    free_text4 character varying(32),
    free_text5 character varying(32),
    emp_id character varying(32),
    branch_id character varying(10)
);


ALTER TABLE public.onp_user_profile OWNER TO bnotonpadm;

--
-- TOC entry 176 (class 1259 OID 50642)
-- Dependencies: 5
-- Name: qrtz_blob_triggers; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_blob_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    blob_data bytea
);


ALTER TABLE public.qrtz_blob_triggers OWNER TO bnotonpadm;

--
-- TOC entry 177 (class 1259 OID 50655)
-- Dependencies: 5
-- Name: qrtz_calendars; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_calendars (
    sched_name character varying(120) NOT NULL,
    calendar_name character varying(200) NOT NULL,
    calendar bytea NOT NULL
);


ALTER TABLE public.qrtz_calendars OWNER TO bnotonpadm;

--
-- TOC entry 174 (class 1259 OID 50616)
-- Dependencies: 5
-- Name: qrtz_cron_triggers; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_cron_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    cron_expression character varying(120) NOT NULL,
    time_zone_id character varying(80)
);


ALTER TABLE public.qrtz_cron_triggers OWNER TO bnotonpadm;

--
-- TOC entry 179 (class 1259 OID 50668)
-- Dependencies: 5
-- Name: qrtz_fired_triggers; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_fired_triggers (
    sched_name character varying(120) NOT NULL,
    entry_id character varying(95) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    instance_name character varying(200) NOT NULL,
    fired_time bigint NOT NULL,
    sched_time bigint NOT NULL,
    priority integer NOT NULL,
    state character varying(16) NOT NULL,
    job_name character varying(200),
    job_group character varying(200),
    is_nonconcurrent boolean,
    requests_recovery boolean
);


ALTER TABLE public.qrtz_fired_triggers OWNER TO bnotonpadm;

--
-- TOC entry 171 (class 1259 OID 50582)
-- Dependencies: 5
-- Name: qrtz_job_details; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_job_details (
    sched_name character varying(120) NOT NULL,
    job_name character varying(200) NOT NULL,
    job_group character varying(200) NOT NULL,
    description character varying(250),
    job_class_name character varying(250) NOT NULL,
    is_durable boolean NOT NULL,
    is_nonconcurrent boolean NOT NULL,
    is_update_data boolean NOT NULL,
    requests_recovery boolean NOT NULL,
    job_data bytea
);


ALTER TABLE public.qrtz_job_details OWNER TO bnotonpadm;

--
-- TOC entry 181 (class 1259 OID 50681)
-- Dependencies: 5
-- Name: qrtz_locks; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_locks (
    sched_name character varying(120) NOT NULL,
    lock_name character varying(40) NOT NULL
);


ALTER TABLE public.qrtz_locks OWNER TO bnotonpadm;

--
-- TOC entry 178 (class 1259 OID 50663)
-- Dependencies: 5
-- Name: qrtz_paused_trigger_grps; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_paused_trigger_grps (
    sched_name character varying(120) NOT NULL,
    trigger_group character varying(200) NOT NULL
);


ALTER TABLE public.qrtz_paused_trigger_grps OWNER TO bnotonpadm;

--
-- TOC entry 180 (class 1259 OID 50676)
-- Dependencies: 5
-- Name: qrtz_scheduler_state; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_scheduler_state (
    sched_name character varying(120) NOT NULL,
    instance_name character varying(200) NOT NULL,
    last_checkin_time bigint NOT NULL,
    checkin_interval bigint NOT NULL
);


ALTER TABLE public.qrtz_scheduler_state OWNER TO bnotonpadm;

--
-- TOC entry 173 (class 1259 OID 50603)
-- Dependencies: 5
-- Name: qrtz_simple_triggers; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_simple_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    repeat_count bigint NOT NULL,
    repeat_interval bigint NOT NULL,
    times_triggered bigint NOT NULL
);


ALTER TABLE public.qrtz_simple_triggers OWNER TO bnotonpadm;

--
-- TOC entry 175 (class 1259 OID 50629)
-- Dependencies: 5
-- Name: qrtz_simprop_triggers; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_simprop_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    str_prop_1 character varying(512),
    str_prop_2 character varying(512),
    str_prop_3 character varying(512),
    int_prop_1 integer,
    int_prop_2 integer,
    long_prop_1 bigint,
    long_prop_2 bigint,
    dec_prop_1 numeric(13,4),
    dec_prop_2 numeric(13,4),
    bool_prop_1 boolean,
    bool_prop_2 boolean
);


ALTER TABLE public.qrtz_simprop_triggers OWNER TO bnotonpadm;

--
-- TOC entry 172 (class 1259 OID 50590)
-- Dependencies: 5
-- Name: qrtz_triggers; Type: TABLE; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE TABLE qrtz_triggers (
    sched_name character varying(120) NOT NULL,
    trigger_name character varying(200) NOT NULL,
    trigger_group character varying(200) NOT NULL,
    job_name character varying(200) NOT NULL,
    job_group character varying(200) NOT NULL,
    description character varying(250),
    next_fire_time bigint,
    prev_fire_time bigint,
    priority integer,
    trigger_state character varying(16) NOT NULL,
    trigger_type character varying(8) NOT NULL,
    start_time bigint NOT NULL,
    end_time bigint,
    calendar_name character varying(200),
    misfire_instr smallint,
    job_data bytea
);


ALTER TABLE public.qrtz_triggers OWNER TO bnotonpadm;

--
-- TOC entry 1856 (class 2604 OID 49780)
-- Dependencies: 162 163 163
-- Name: msgid; Type: DEFAULT; Schema: public; Owner: bnotonpadm
--

ALTER TABLE ONLY onp_bank_msg_format ALTER COLUMN msgid SET DEFAULT nextval('onp_bank_msg_format_msgid_seq'::regclass);


--
-- TOC entry 1857 (class 2604 OID 49817)
-- Dependencies: 167 168 168
-- Name: msgid; Type: DEFAULT; Schema: public; Owner: bnotonpadm
--

ALTER TABLE ONLY onp_custom_message_format ALTER COLUMN msgid SET DEFAULT nextval('onp_custom_message_format_msgid_seq'::regclass);


--
-- TOC entry 1860 (class 2606 OID 49782)
-- Dependencies: 163 163 2018
-- Name: onp_bank_msg_format_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY onp_bank_msg_format
    ADD CONSTRAINT onp_bank_msg_format_pkey PRIMARY KEY (msgid);


--
-- TOC entry 1866 (class 2606 OID 49822)
-- Dependencies: 168 168 2018
-- Name: onp_custom_message_format_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY onp_custom_message_format
    ADD CONSTRAINT onp_custom_message_format_pkey PRIMARY KEY (msgid);


--
-- TOC entry 1910 (class 2606 OID 58918)
-- Dependencies: 182 182 2018
-- Name: onp_customer_reg_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY onp_customer_reg
    ADD CONSTRAINT onp_customer_reg_pkey PRIMARY KEY (mobilenumber);


--
-- TOC entry 1862 (class 2606 OID 49787)
-- Dependencies: 164 164 2018
-- Name: onp_property_manager_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY onp_property_manager
    ADD CONSTRAINT onp_property_manager_pkey PRIMARY KEY (property_name);


--
-- TOC entry 1864 (class 2606 OID 49801)
-- Dependencies: 166 166 2018
-- Name: onp_user_profile_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY onp_user_profile
    ADD CONSTRAINT onp_user_profile_pkey PRIMARY KEY (userid);


--
-- TOC entry 1892 (class 2606 OID 50649)
-- Dependencies: 176 176 176 176 2018
-- Name: qrtz_blob_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_blob_triggers
    ADD CONSTRAINT qrtz_blob_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- TOC entry 1894 (class 2606 OID 50662)
-- Dependencies: 177 177 177 2018
-- Name: qrtz_calendars_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_calendars
    ADD CONSTRAINT qrtz_calendars_pkey PRIMARY KEY (sched_name, calendar_name);


--
-- TOC entry 1888 (class 2606 OID 50623)
-- Dependencies: 174 174 174 174 2018
-- Name: qrtz_cron_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_cron_triggers
    ADD CONSTRAINT qrtz_cron_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- TOC entry 1904 (class 2606 OID 50675)
-- Dependencies: 179 179 179 2018
-- Name: qrtz_fired_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_fired_triggers
    ADD CONSTRAINT qrtz_fired_triggers_pkey PRIMARY KEY (sched_name, entry_id);


--
-- TOC entry 1870 (class 2606 OID 50589)
-- Dependencies: 171 171 171 171 2018
-- Name: qrtz_job_details_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_job_details
    ADD CONSTRAINT qrtz_job_details_pkey PRIMARY KEY (sched_name, job_name, job_group);


--
-- TOC entry 1908 (class 2606 OID 50685)
-- Dependencies: 181 181 181 2018
-- Name: qrtz_locks_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_locks
    ADD CONSTRAINT qrtz_locks_pkey PRIMARY KEY (sched_name, lock_name);


--
-- TOC entry 1896 (class 2606 OID 50667)
-- Dependencies: 178 178 178 2018
-- Name: qrtz_paused_trigger_grps_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_paused_trigger_grps
    ADD CONSTRAINT qrtz_paused_trigger_grps_pkey PRIMARY KEY (sched_name, trigger_group);


--
-- TOC entry 1906 (class 2606 OID 50680)
-- Dependencies: 180 180 180 2018
-- Name: qrtz_scheduler_state_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_scheduler_state
    ADD CONSTRAINT qrtz_scheduler_state_pkey PRIMARY KEY (sched_name, instance_name);


--
-- TOC entry 1886 (class 2606 OID 50610)
-- Dependencies: 173 173 173 173 2018
-- Name: qrtz_simple_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_simple_triggers
    ADD CONSTRAINT qrtz_simple_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- TOC entry 1890 (class 2606 OID 50636)
-- Dependencies: 175 175 175 175 2018
-- Name: qrtz_simprop_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_simprop_triggers
    ADD CONSTRAINT qrtz_simprop_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- TOC entry 1884 (class 2606 OID 50597)
-- Dependencies: 172 172 172 172 2018
-- Name: qrtz_triggers_pkey; Type: CONSTRAINT; Schema: public; Owner: bnotonpadm; Tablespace: 
--

ALTER TABLE ONLY qrtz_triggers
    ADD CONSTRAINT qrtz_triggers_pkey PRIMARY KEY (sched_name, trigger_name, trigger_group);


--
-- TOC entry 1897 (class 1259 OID 50701)
-- Dependencies: 179 179 179 2018
-- Name: idx_qrtz_ft_inst_job_req_rcvry; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_ft_inst_job_req_rcvry ON qrtz_fired_triggers USING btree (sched_name, instance_name, requests_recovery);


--
-- TOC entry 1898 (class 1259 OID 50702)
-- Dependencies: 179 179 179 2018
-- Name: idx_qrtz_ft_j_g; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_ft_j_g ON qrtz_fired_triggers USING btree (sched_name, job_name, job_group);


--
-- TOC entry 1899 (class 1259 OID 50703)
-- Dependencies: 179 179 2018
-- Name: idx_qrtz_ft_jg; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_ft_jg ON qrtz_fired_triggers USING btree (sched_name, job_group);


--
-- TOC entry 1900 (class 1259 OID 50704)
-- Dependencies: 179 179 179 2018
-- Name: idx_qrtz_ft_t_g; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_ft_t_g ON qrtz_fired_triggers USING btree (sched_name, trigger_name, trigger_group);


--
-- TOC entry 1901 (class 1259 OID 50705)
-- Dependencies: 179 179 2018
-- Name: idx_qrtz_ft_tg; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_ft_tg ON qrtz_fired_triggers USING btree (sched_name, trigger_group);


--
-- TOC entry 1902 (class 1259 OID 50700)
-- Dependencies: 179 179 2018
-- Name: idx_qrtz_ft_trig_inst_name; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_ft_trig_inst_name ON qrtz_fired_triggers USING btree (sched_name, instance_name);


--
-- TOC entry 1867 (class 1259 OID 50687)
-- Dependencies: 171 171 2018
-- Name: idx_qrtz_j_grp; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_j_grp ON qrtz_job_details USING btree (sched_name, job_group);


--
-- TOC entry 1868 (class 1259 OID 50686)
-- Dependencies: 171 171 2018
-- Name: idx_qrtz_j_req_recovery; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_j_req_recovery ON qrtz_job_details USING btree (sched_name, requests_recovery);


--
-- TOC entry 1871 (class 1259 OID 50690)
-- Dependencies: 172 172 2018
-- Name: idx_qrtz_t_c; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_c ON qrtz_triggers USING btree (sched_name, calendar_name);


--
-- TOC entry 1872 (class 1259 OID 50691)
-- Dependencies: 172 172 2018
-- Name: idx_qrtz_t_g; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_g ON qrtz_triggers USING btree (sched_name, trigger_group);


--
-- TOC entry 1873 (class 1259 OID 50688)
-- Dependencies: 172 172 172 2018
-- Name: idx_qrtz_t_j; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_j ON qrtz_triggers USING btree (sched_name, job_name, job_group);


--
-- TOC entry 1874 (class 1259 OID 50689)
-- Dependencies: 172 172 2018
-- Name: idx_qrtz_t_jg; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_jg ON qrtz_triggers USING btree (sched_name, job_group);


--
-- TOC entry 1875 (class 1259 OID 50694)
-- Dependencies: 172 172 172 2018
-- Name: idx_qrtz_t_n_g_state; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_n_g_state ON qrtz_triggers USING btree (sched_name, trigger_group, trigger_state);


--
-- TOC entry 1876 (class 1259 OID 50693)
-- Dependencies: 172 172 172 172 2018
-- Name: idx_qrtz_t_n_state; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_n_state ON qrtz_triggers USING btree (sched_name, trigger_name, trigger_group, trigger_state);


--
-- TOC entry 1877 (class 1259 OID 50695)
-- Dependencies: 172 172 2018
-- Name: idx_qrtz_t_next_fire_time; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_next_fire_time ON qrtz_triggers USING btree (sched_name, next_fire_time);


--
-- TOC entry 1878 (class 1259 OID 50697)
-- Dependencies: 172 172 172 2018
-- Name: idx_qrtz_t_nft_misfire; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_nft_misfire ON qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time);


--
-- TOC entry 1879 (class 1259 OID 50696)
-- Dependencies: 172 172 172 2018
-- Name: idx_qrtz_t_nft_st; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_nft_st ON qrtz_triggers USING btree (sched_name, trigger_state, next_fire_time);


--
-- TOC entry 1880 (class 1259 OID 50698)
-- Dependencies: 172 172 172 172 2018
-- Name: idx_qrtz_t_nft_st_misfire; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_nft_st_misfire ON qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time, trigger_state);


--
-- TOC entry 1881 (class 1259 OID 50699)
-- Dependencies: 172 172 172 172 172 2018
-- Name: idx_qrtz_t_nft_st_misfire_grp; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_nft_st_misfire_grp ON qrtz_triggers USING btree (sched_name, misfire_instr, next_fire_time, trigger_group, trigger_state);


--
-- TOC entry 1882 (class 1259 OID 50692)
-- Dependencies: 172 172 2018
-- Name: idx_qrtz_t_state; Type: INDEX; Schema: public; Owner: bnotonpadm; Tablespace: 
--

CREATE INDEX idx_qrtz_t_state ON qrtz_triggers USING btree (sched_name, trigger_state);


--
-- TOC entry 1915 (class 2606 OID 50650)
-- Dependencies: 176 172 172 1883 176 172 176 2018
-- Name: qrtz_blob_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bnotonpadm
--

ALTER TABLE ONLY qrtz_blob_triggers
    ADD CONSTRAINT qrtz_blob_triggers_sched_name_fkey FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers(sched_name, trigger_name, trigger_group);


--
-- TOC entry 1913 (class 2606 OID 50624)
-- Dependencies: 172 172 174 174 174 172 1883 2018
-- Name: qrtz_cron_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bnotonpadm
--

ALTER TABLE ONLY qrtz_cron_triggers
    ADD CONSTRAINT qrtz_cron_triggers_sched_name_fkey FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers(sched_name, trigger_name, trigger_group);


--
-- TOC entry 1912 (class 2606 OID 50611)
-- Dependencies: 172 173 173 173 172 172 1883 2018
-- Name: qrtz_simple_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bnotonpadm
--

ALTER TABLE ONLY qrtz_simple_triggers
    ADD CONSTRAINT qrtz_simple_triggers_sched_name_fkey FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers(sched_name, trigger_name, trigger_group);


--
-- TOC entry 1914 (class 2606 OID 50637)
-- Dependencies: 1883 172 175 175 172 175 172 2018
-- Name: qrtz_simprop_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bnotonpadm
--

ALTER TABLE ONLY qrtz_simprop_triggers
    ADD CONSTRAINT qrtz_simprop_triggers_sched_name_fkey FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers(sched_name, trigger_name, trigger_group);


--
-- TOC entry 1911 (class 2606 OID 50598)
-- Dependencies: 171 171 1869 172 172 172 171 2018
-- Name: qrtz_triggers_sched_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bnotonpadm
--

ALTER TABLE ONLY qrtz_triggers
    ADD CONSTRAINT qrtz_triggers_sched_name_fkey FOREIGN KEY (sched_name, job_name, job_group) REFERENCES qrtz_job_details(sched_name, job_name, job_group);


--
-- TOC entry 2021 (class 0 OID 0)
-- Dependencies: 170
-- Name: onp_audittable; Type: ACL; Schema: public; Owner: bnotonpadm
--

REVOKE ALL ON TABLE onp_audittable FROM PUBLIC;
REVOKE ALL ON TABLE onp_audittable FROM bnotonpadm;
GRANT ALL ON TABLE onp_audittable TO bnotonpadm;


--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 163
-- Name: onp_bank_msg_format; Type: ACL; Schema: public; Owner: bnotonpadm
--

REVOKE ALL ON TABLE onp_bank_msg_format FROM PUBLIC;
REVOKE ALL ON TABLE onp_bank_msg_format FROM bnotonpadm;
GRANT ALL ON TABLE onp_bank_msg_format TO bnotonpadm;


--
-- TOC entry 2024 (class 0 OID 0)
-- Dependencies: 169
-- Name: onp_broadcast_msg; Type: ACL; Schema: public; Owner: bnotonpadm
--

REVOKE ALL ON TABLE onp_broadcast_msg FROM PUBLIC;
REVOKE ALL ON TABLE onp_broadcast_msg FROM bnotonpadm;
GRANT ALL ON TABLE onp_broadcast_msg TO bnotonpadm;


--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 168
-- Name: onp_custom_message_format; Type: ACL; Schema: public; Owner: bnotonpadm
--

REVOKE ALL ON TABLE onp_custom_message_format FROM PUBLIC;
REVOKE ALL ON TABLE onp_custom_message_format FROM bnotonpadm;
GRANT ALL ON TABLE onp_custom_message_format TO bnotonpadm;


--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 165
-- Name: onp_dnd_mobile_reg; Type: ACL; Schema: public; Owner: bnotonpadm
--

REVOKE ALL ON TABLE onp_dnd_mobile_reg FROM PUBLIC;
REVOKE ALL ON TABLE onp_dnd_mobile_reg FROM bnotonpadm;
GRANT ALL ON TABLE onp_dnd_mobile_reg TO bnotonpadm;


--
-- TOC entry 2028 (class 0 OID 0)
-- Dependencies: 164
-- Name: onp_property_manager; Type: ACL; Schema: public; Owner: bnotonpadm
--

REVOKE ALL ON TABLE onp_property_manager FROM PUBLIC;
REVOKE ALL ON TABLE onp_property_manager FROM bnotonpadm;
GRANT ALL ON TABLE onp_property_manager TO bnotonpadm;


-- Completed on 2016-03-20 20:51:59 IST

--
-- PostgreSQL database dump complete
--

