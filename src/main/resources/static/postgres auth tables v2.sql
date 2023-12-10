/* 
* create new schema ds1 for admin
*/
create schema ds1; 

/* 
* then craete tables
* create tables and data for springboot framework performing authentication
*/

-- user is an identity table
create table ds1.user (
    uuid char(36),
    username varchar(50) not null unique,
    password varchar(72) not null, -- bcrypted password
    is_account_non_expired boolean, -- isAccountNonExpired
    is_account_non_locked boolean, -- isAccountNonLocked
    is_credentials_non_expired boolean, -- isCredentialsNonExpired
    is_enabled boolean, -- isEnabled
    date_created date,
    created_by varchar(50),
    last_updated date,
    last_updated_by varchar(50),
    
    constraint user_pk primary key (uuid)
);

-- role is authority table
create table ds1.role (
    uuid char(36) primary key,
    role_name varchar(20) not null
);

-- a junction table between user and role
create table ds1.user_role (
   user_id char(36) references ds1.user(uuid),
   role_id char(36) references ds1.role(uuid),
   
   constraint user_role_uk primary key(user_id,role_id)
);


-- admin account

insert into ds1.user values ('2b9ec081-d4b2-4d83-98b1-581995bc7486', 'admin', '$2a$10$iHJIjrj3ma7C/abparQ/UuqVQW1h5w2isFaG7aC8AKXN640Vii9HK', true, true, true, true, now(), 'dbadmin', now(), 'dbadmin');
insert into ds1.role values ('05e6a5de-1f47-4e62-b76a-1272bc180d28','ROLE_ADMIN');
insert into public.user_role
select a.uuid user_id, b.uuid role_id from ds1.user a, ds1.role b
where username = 'admin' and role_name in ('ROLE_ADMIN','ROLE_USER');

