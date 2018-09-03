var express = require('express');
var http = require('http');
var path = require('path');
var mysql = require('mysql');
var cors = require('cors');
var bodyParser = require('body-parser');
var app = express();
app.set('port',process.env.PORT || 3000);

app.use(bodyParser.json());

app.use(cors());
var router = express.Router();

var connection = mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password : 'dbsals123',
    port : '3306',
    database : 'timesale_db'
});

// 오너가 회원가입할 때 자동으로 쿠폰번호 1,2,3이 Null로 등록되야 편할듯.
// 오너는 최대 두개의 쿠폰만 등록할 수 있음
// 표 형식으로 쿠폰 두개만 입력할 수 있는 칸을 만들면 될듯 -> 아무것도 적지 않으면 널이 자동으로 들어감.

var server = http.createServer(app).listen(app.get('port'),()=>{
    console.log('서버시작');
})

//timeAttack 쿠폰은 우리가 시간을 정해서 시간이 끝나면 자동으로 파기 되게끔 등록하기.

app.get('/allGift',(req,res)=>{ // 디비 안에 있는 쿠폰목록 중 오너가 올리겠다고 한 것만 알려주는 것
    console.log('점주들이 등록한 모든 쿠폰');

    var category = req.query.coupon_category || req.body.coupon_category;

    console.log('category : '+category);
    connection.query('select market_name,coupon_content,coupon_key from owner_list,all_couponlist where (coupon1=coupon_key or coupon2=coupon_key or time_attack=coupon_key) and on_off=\'T\' and category =\"'+category+'\"'
        ,(err,rows)=>{
            console.log(rows);
            res.json(rows);
        });
});

// 오너가 만약 쿠폰을 등록할 때 이전 쿠폰 등록 그대로 띄어주는 지 물어보는 거 있어야 할듯

app.post('/registerCoupon',(req,res)=>{ // 안드로이드에서 오너가 쿠폰등록을 누르면 디비에 저장 되는 곳
    // var user_key = req.body.user_key;
    var coupon_key = req.body.coupon_key;
    var coupon_content = req.body.coupon_content;
    var coupon_count = req.body.coupon_count;
    var coupon_expired_date=req.body.coupon_expired_date;
    console.log('쿠폰등록');
    console.log('key = '+coupon_key+'content = '+coupon_content+'count='+coupon_count+'date='+coupon_expired_date);

    // res.send("update 완료");

    connection.query('update all_couponlist set coupon_content = \"'+coupon_content+'\",on_off = \'T\',coupon_count='+coupon_count+',coupon_expired_date=\"'+coupon_expired_date+'\" where coupon_key = '+coupon_key
        ,(err,rows)=>{
            res.json(rows);
        })

})

app.get('/showOwnerRegisterCoupon',(req,res)=>{ // user_key랑 동일한 쿠폰키중에 all_couponlist에 동일한 키 출력
    var user_key = req.body.user_key || req.query.user_key;
    console.log('showMyCoupon 호출');
    console.log('user_key : '+user_key);
    connection.query('select a.coupon_content,a.coupon_key,a.on_off from all_couponlist a, owner_list b where b.user_key='+user_key+' and (a.coupon_key = b.coupon1 or a.coupon_key = b.coupon2 or a.coupon_key = b.time_attack)'
        ,(err,rows)=>{
            res.json(rows);
        })
})

app.get('/show_id_pw',(req,res)=>{
    connection.query('select id,pw,who_key,user_key from(select id,pw,who_key,user_key from owner_list union select id,pw,who_key,user_key from client_list)x'
        ,(err,rows)=>{
            if(err) throw err;
            console.log(rows);
            res.json(rows);
        })
})

app.get('/showClientHaveCoupon',(req,res)=>{
    var user_key = req.body.user_key || req.query.user_key;
    console.log(user_key);
    connection.query('select coupon_key,market_name, coupon_content,on_off from client_list,coupon_view where client_list.user_key = '+user_key+' and (coupon1=coupon_key or coupon2=coupon_key or time_attack =coupon_key)'
        ,(err,rows)=>{
            if(err) throw err;
            console.log(rows);
            res.json(rows);
        })
})

app.post('/getClientCoupon',(req,res)=>{
    var coupon1=req.body.coupon1;
    var coupon2=req.body.coupon2;
    var time_attack=req.body.time_attack;
    var user_key=req.body.user_key;
    console.log('사용자 키를 업데이트');
    connection.query('update client_list set coupon1='+coupon1+',coupon2='+coupon2+',time_attack='+time_attack+' where user_key = '+user_key
        ,(err,rows)=>{
            if(err) throw err;
            res.json(rows);
        })
})

app.get('/clientKey',(req,res)=>{
    var user_key = req.body.user_key || req.query.user_key;
    console.log('사용자가 가지고 있는 coupon1,coupon2,timeAttack 호출');
    connection.query('select coupon1,coupon2,time_attack from client_list where user_key = '+user_key
        ,(err,rows)=>{
            if(err) throw err;
            res.json(rows);
        })
})

app.post('/deleteOwnerCoupon',(req,res)=>{
    console.log('/deleteClientCoupon 호출');
    var coupon_key = req.body.delete_key || req.query.delete_key;
    console.log('coupon_key='+coupon_key);
    connection.query('update all_couponlist set on_off=\'F\' where coupon_key='+coupon_key
        ,(err,rows)=>{
            if(err) throw err;
            res.json(rows);
            console.log('client의 쿠폰 한개 삭제');
        })

})

app.get('/showMarketInfo',(req,res)=>{
    console.log('/showMarketInfo 호출');
    var coupon_key = req.body.couponKey || req.query.couponKey;
    connection.query('select market_name,user_key,address,introduce,phone from coupon_view where coupon_key='+coupon_key
        ,(err,rows)=>{
            if(err) throw err;
            res.json(rows);
            console.log(rows);
        })
})

app.get('/overlapID',(req,res)=>{
    var id = req.body.id || req.query.id;
    console.log('/overlapID 호출');
    console.log("id : "+id);
    connection.query('select id from show_id_pw where id=\"'+id+'\"'
        ,(err,rows)=>{
            if(err) throw err;
            console.log(rows);
            res.json(rows);
        })
})

app.post('/signUpClient',(req,res)=>{
    console.log('/signUpClient 호출');
    var id = req.body.id || req.query.id;
    var pw = req.body.pw || req.query.pw;
    var name = req.body.name || req.query.name;
    var phone = req.body.phone || req.query.phone;
    var user_key;
    connection.query('select * from client_list order by user_key desc limit 1'
        ,(err,rows)=>{
            console.log(rows);
            // var userKeyObj = JSON.parse(rows);
            user_key = rows[0].user_key+1;
            // console.log(userKeyObj);
            // user_key = userKeyObj.user_key;
            console.log('유저키 : '+user_key);
            connection.query('insert into client_list(user_key,who_key,ID,PW,NAME,PHONE,coupon1,coupon2,time_attack) VALUES('+user_key+',\'C\',\''+id+'\',\''+pw+'\',\''+name+'\',\''+phone+'\',-1,-1,-1)'
                ,(err,rows)=>{
                    if(err) res.send("-1");
                    else res.send("1");
                })
        })
    // connection.query('insert into client_list(user_key,ID,PW, VALUES')
})

app.get('/favorite',(req,res)=>{ // client가 이 마켓을 즐겨찾기로 등록했는지 안했는지 알아보기 위함
    console.log('/favorite 호출');
    var client_key = req.body.client_key || req.query.client_key;
    var owner_key = req.body.owner_key || req.query.owner_key;
    console.log('client_key : '+client_key);
    console.log('owner_key : '+owner_key);
    connection.query('select * from favorite where client_key='+client_key+' and owner_key='+owner_key,
        (err,rows)=>{
            console.log(rows);
            res.json(rows);
        })

})

app.post('/favoriteRegister',(req,res)=>{
    console.log('/favorite/register 호출');
    var client_key = req.body.client_key || req.query.client_key;
    var owner_key = req.body.owner_key || req.query.owner_key;
    connection.query('INSERT INTO favorite VALUES('+client_key+','+owner_key+')'
    ,(err,rows)=>{
        if(err) throw err;
            res.send("1");
        })
})

app.post('/favoriteDelete',(req,res)=>{
    console.log('/favorite/delete 호출');
    var client_key = req.body.client_key || req.query.client_key;
    var owner_key = req.body.owner_key || req.query.owner_key;
    connection.query('DELETE FROM favorite where client_key = '+client_key+' and owner_key = '+owner_key
        ,(err,rows)=>{
            if(err) throw err;
            res.send("1");
        })
})

app.get('/clientInfo',(req,res)=>{
    console.log('/clientInfo 호출');
    var user_key = req.body.user_key || req.query.user_key;
    console.log('userKey : '+user_key);
    connection.query('select nickName,phone,alarm_push,alarm_mail,alarm_SMS from client_list where user_key='+user_key // nickName 생기면 넣어주기.
        ,(err,rows)=>{
            if(err) throw err;
            res.json(rows);
        })
})

app.delete('/deleteClientInfo',(req,res)=>{
    console.log('/deleteClientInfo 호출');
    var user_key = req.body.user_key || req.query.user_key;
    console.log(user_key);

    connection.query('delete from client_list where user_key = '+user_key
    ,(err,rows)=>{
        if(err) throw err;
        res.json(rows);
        })
})

app.put('/changeClientInfo',(req,res)=>{
    console.log('/changeClientInfo 호출');
    var user_key = req.body.user_key || req.query.user_key;
    var nickName = req.body.nickName || req.query.nickName;
    var pw = req.body.pw || req.query.pw;
    var alarm_push = req.body.alarm_push || req.query.alarm_push;
    var alarm_mail = req.body.alarm_mail || req.query.alarm_mail;
    var alarm_SMS = req.body.alarm_SMS || req.query.alarm_SMS;

    console.log("alarm_SMS : "+alarm_SMS);
    connection.query('update client_list set nickName = \"'+nickName+'\",PW = \"'+pw+'\", alarm_push = \"'+alarm_push+'\", alarm_mail = \"'+alarm_mail+'\", alarm_SMS = \"'+alarm_SMS+'\" where user_key = '+user_key
        ,(err,rows)=>{
            if(err) throw err;
            res.send("1");
        })
})

app.use('/',router);

/*

고객 센터 -> 카카오톡 ID 정도만 알려주기.

client 쿠폰 갯수 5개

점주 처음 등록할 때 category 등록하기
client가 모든 쿠폰 목록 버튼을 누르면 category 선택할 수 있게 하기
 */