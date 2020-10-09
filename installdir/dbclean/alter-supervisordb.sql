
use supervisor;

drop procedure if exists alter_db;
delimiter //

create procedure alter_db()
begin
  declare version int;
  set version=0;
  select value from props where propid=1 and category='db' and name='version' INTO version;



end //
delimiter ;

call alter_db();
drop procedure alter_db;

#########################################################


-- update to current dbversion
update `props` set value=1 where propid=1;


