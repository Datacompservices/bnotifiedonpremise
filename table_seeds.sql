-- insert scripts needs to be executed after table creation

INSERT INTO ONP_Property_manager values ('CHANNEL_ID','BNOT');
INSERT INTO ONP_Property_manager values ('CORP_ID','BNOT');
INSERT INTO ONP_Property_manager values ('ENTITY_SHORTNAME',''); 
INSERT INTO ONP_Property_manager values ('UPLOAD_FILE_PATH',''); 
INSERT INTO ONP_Property_manager values ('BANK_ID',''); 
INSERT INTO ONP_Property_manager values ('DEFAULT_BRANCH_ID','');
INSERT INTO ONP_Property_manager values ('ENTITY_LOGO','');
""


INSERT INTO onp_user_profile values('$ENTITY_ID$','CORPID','SU','JiaUhRgJ2BTcmTaYOou2bh/TCD4=',0,'M/s.','FNAME','SNAME','LNAME','','',now(),now(),'N',now(),now(),'N','setup',now(),'setup',now(),'','','','','','','0000');

insert into ONP_Bank_MSG_FORMAT(bankID,branchid,msg_lang,msg_channel_id,messagetext,del_flg,r_cre_id,r_cre_time,r_mod_id,r_mod_time)
values('$ENTITY_ID$','0000','en-us','BNOT','welcome message','N','setup',now(),'setup',now());

