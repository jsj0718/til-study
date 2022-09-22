/***** 변수 *****/
/** var 특징
 * 1. 한 번 선언된 변수를 다시 선언 가능
 * 2. var는 선언하기 전에 사용 가능 (호이스팅: Scope 내부 어디서든 변순 선언이 최상위에 선언된 것처럼 행동하는 것)
 * 3. 단 let과 const도 호이스팅 당하지만, var는 함수 스코프이고 let과 const는 블록 스코프이기 때문에 차이가 발생한다.
 */

/* 특징 1 */
// var name = 'Mike';
// console.log(name);

// var name = 'Jane';
// console.log(name)

/* 특징 2 */
//형태1 (호이스팅에 의해 형태2와 같은 구조가 된다.)
// console.log(name); //undefined
// var name = 'Mike';

//형태2
// var age;
// console.log(age); //undefined
// age = 23;

/** Temporal Dead Zone (TDZ)
 * - let과 const는 TDZ에 영향을 받는다. (호이스팅이 되지만 TDZ에 의해 방지되는 것)
 */
// console.log(name); //TDZ (name 사용 불가능)
// const name = 'Mike'; //변수 선언 및 할당
// console.log(name); //name 사용 가능

// let age = 30;
// function showAge() {
//     console.log(age);
// let age = 20; //호이스팅에 의해 실행 오류 발생
// }
// showAge();


/** 변수 생성 과정
 * - var: 1.선언 및 초기화 단계 -> 2.할당 단계 (함수 스코프)
 * - let: 1.선언 단계 -> 2.초기화 단계 -> 3.할당 단계 (블록 스코프)
 * - const: 1.선언 + 초기화 + 할당 (블록 스코프)
 */

/** var 스코프 **/
/* 블록 스코프 */
// const age = 30;
// if (age > 19) {
//     var txt = '성인'; //let과 const는 이렇게 사용 불가능
// }
// console.log(txt);

/* 함수 스코프 */
// function add(num1, num2) {
//     var result = num1 + num2;
// }
// add(2, 3);
// console.log(result); //오류 발생


/***** 생성자 함수 *****/
/*
function User(name, age) {
    // this = {};

    this.name = name;
    this.age = age;
    this.sayName = function () {
        console.log(this.name);
    };

    // return this;
}

let user1 = new User('Mike', 30);
let user2 = User('Jane', 22); //undefined
let user3 = new User('Tom', 17);

console.log(user1, user2, user3);

user1.sayName(); //Mike
user3.sayName(); //Tom
*/

/***** methods와 computed property *****/
/** computed property **/
// let a = 'age';
// const user = {
//     name: 'Mike',
//     [a]: 30, //key에 age 할당
// }
// console.log(user);

// const test = {
//     [1+4]: 5,
//     ['안녕' + '하세요']: 'Hello'
// }
// console.log(test);

/*** methods ***/

/** Object.assign(): 객체 복제 **/

/* 예제1 */
/*
const user = {
    name: 'Mike',
    age: 30
}

const newUser = Object.assign({}, user);
newUser.name = 'Jane';

console.log(user.name); //Mike
console.log(newUser.name); //Jane
*/

/* 예제2 (초기값 할당) */
/*
const user = {
    name: 'Mike',
    age: 30
}

const newUser = Object.assign({name: 'Tom', gender: 'male'}, user);
console.log(newUser.name); //Tom -> Mike
console.log(newUser.gender); //male
*/

/* 예제3 */
/*
const user = {
    name: 'Mike',
}

const info1 = {
    age: 30
}

const info2 = {
    gender: 'male'
}

Object.assign(user, info1, info2);
console.log(user);
*/

/** Object.keys(): 키 배열 반환, Object.values(): 값 배열 반환, Object.entries(): 키와 값 배열 반환 **/
/*
const user = {
    name: 'Mike',
    age: 30,
    gender: 'male'
}

console.log(Object.keys(user));
console.log(Object.values(user));
console.log(Object.entries(user));
*/

/** Object.fromEntries(): 키와 값 배열을 객체로 생성 후 반환 **/
/*
const arr = [
    ['name', 'Mike'],
    ['age', 30],
    ['gender', 'male']
]

console.log(Object.fromEntries(arr));
*/

/* 예제1 */
/*
function makeObj(key, val) {
    return {
        [key]: val,
    };
}

console.log(makeObj('name', 'Mike'));
console.log(makeObj('age', 23));
console.log(makeObj('성별', 'male'));
*/

/***** Symbol *****/
/** property key: 문자형 **/
/*
const obj = {
    1: '1이다.',
    false: '거짓'
}

console.log(Object.keys(obj));
console.log(obj['1'], obj['false']);
*/

/** Symbol **/
/* 유일한 식별자로 new를 붙이지 않는다. */
/*
const a = Symbol();
const b = Symbol();
console.log(a == b, a === b);

const id = Symbol('id');
const user = {
    name: 'Mike',
    age: 30,
    [id]: 'myId',
};
console.log(user); //{..., [Symbol(id)] : 'myId'}
console.log(user[id]) //myId
/!* Symbol(id)는 포함되지 않는다. *!/
console.log(Object.keys(user));
console.log(Object.values(user));
console.log(Object.entries(user));
*/

/*
const user = {
    name: 'Mike',
    age: 30
}

const id = Symbol('id');
const name = Symbol('name');
const name2 = Symbol('name'); //name과 다른 Symbol 생성
user[id] = 'myId';
user[name] = 'myName';
user[name2] = 'myName2';

console.log(user); //{name: 'Mike', age: 30, [Symbol(id)]: 'myId', [Symbol(name)]: 'myName'}
console.log(user[name]); //myName
console.log(user[name2]); //myName2
console.log(name.description); //name
console.log(name2.description); //name
*/

/** Symbol.for(): 전역 심볼
 * - 하나의 심볼만 보장받을 수 있다.
 * - 없으면 만들고, 있으면 가져오기 때문이다.
 * - Symbol()은 매번 Symbol값을 생성하지만, Symbol.for()는 하나 생성 후 키를 통해 같은 Symbol을 공유한다.
 */
/*
const id1 = Symbol.for('id');
const id2 = Symbol.for('id');
console.log(id1 === id2);
console.log(Symbol.keyFor(id1));
console.log(Symbol.keyFor(id2));
*/

/** 숨겨진 Symbol Key 조회 **/
/*
const id = Symbol('id');

const user = {
    name: 'Mike',
    age: 30,
    [id]: 'myId',
}

console.log(Object.getOwnPropertySymbols(user));

for (let key in user) {
    console.log(`His ${key} is ${user[key]}`);
}
*/

/*** Number, Math ***/

/** toString() **/
/* 10진수 -> 2진수 또는 16진수 */
/*
let num1 = 10;
console.log(num1.toString(2)); //1010

let num2 = 255;
console.log(num2.toString(16)); //ff
*/

/** Math **/
// console.log(Math.PI);

// let num1 = 5.1;
// let num2 = 5.7;

/* ceil() */
// console.log(Math.ceil(num1));
// console.log(Math.ceil(num2));

/* floor() */
// console.log(Math.floor(num1));
// console.log(Math.floor(num2));

/* round() */
// console.log(Math.round(num1));
// console.log(Math.round(num2));

/* toFixed(): 소수점 자릿수 */
// let userRate = 30.1234;
// console.log(userRate.toFixed(2)); //30.12
// console.log(userRate.toFixed(0)); //30

/* isNaN(): NaN 여부 판단 */
// console.log(Number('x') == NaN); //false
// console.log(Number('x') === NaN); //false
// console.log(isNaN(Number('x'))); //true
// console.log(isNaN(Number('1'))); //false

/* parseInt() */
// let margin = '10px';
// console.log(parseInt(margin)); //10

// let redColor = 'f3';
// console.log(parseInt(redColor)); //NaN
// console.log(parseInt(redColor, 16)); //243

/* parseFloat() */
// let padding = '18.5%';
// console.log(parseInt(padding)); //18
// console.log(parseFloat(padding)); //18.5

/* random() */
// let random = Math.floor(Math.random() * 100) + 1;
// console.log(random);

/* max()와 min() */
// let max = Math.max(1, 4, -1, 5, 10, 9, 5.54);
// let min = Math.min(1, 4, -1, 5, 10, 9, 5.54);
// console.log(max); //10
// console.log(min); //-1

/* 기타 함수 */
// console.log(Math.abs(-1)); //1
// console.log(Math.pow(2, 10)); //1024
// console.log(Math.sqrt(16)); //4

/***** String *****/
/** 함수 **/
/* indexOf() */
// let desc = "Hi guys, Nice to meet you.";
// console.log(desc.indexOf('to')); //14
// console.log(desc.indexOf('man')); //-1

// if (desc.indexOf('Hi')) { //0이므로 false 처리
//     console.log('Hi가 포함된 문장');
// }

// if (desc.indexOf('Hi') > -1) {
//     console.log('Hi가 포함된 문장');
// }

/* slice() */
// let desc = 'abcdefg';
// console.log(desc.slice(2)); //cdefg
// console.log(desc.slice(0, 5)); //abcde
// console.log(desc.slice(2, -2)); //cde

/* substring(n, m): n과 m 사이 문자열 반환(n, m이 바뀌어도 동일한 결과), 음수는 0으로 인식 */
// let desc = 'abcdefg';
// console.log(desc.substring(2, 5)); //cde
// console.log(desc.substring(5, 2)); //cde


/* substr(n, m): n부터 시작해서 m개를 가져옴 */
// let desc = 'abcdefg';
// console.log(desc.substr(2, 4)); //cdef
// console.log(desc.substr(-4, 2)); //de

/* trim(): 공백 제거 */
// let desc = " coding       ";
// console.log(desc.trim()); //coding

/* repeat(n): n번 반복 */
// let hello = 'hello!';
// console.log(hello.repeat(3)); //hello!hello!hello!

/** 문자열 비교 **/
// console.log('a'.codePointAt(0)); //97
// console.log(String.fromCodePoint(97)); //a

/** 예제 **/
/* 1. 숫자 제외한 문자열만 리스트에 담기 */
/*
let list = [
    "01. 들어가며",
    "02. JS의 역사",
    "03. 자료형",
    "04. 함수",
    "05. 배열",
]

let newList = [];
for (let element of list) {
    newList.push(element.substring(4));
}
console.log(newList);
*/

/* 2. 금칙어: 콜라 */
/*
const hasCoke = str => {
    // if (str.indexOf('콜라') > -1) {
    if (str.includes('콜라')) {
        console.log('금칙어 포함');
    } else {
        console.log('금칙어 미포함');
    }
}

hasCoke('콜라, 사이다, 환타');
hasCoke('사이다, 환타, 콜라');
hasCoke('사이다, 환타');
*/

/***** Array *****/

/*** 함수 ***/
/* push(): 뒤에 삽입 */
/* pop(): 뒤에 삭제 */
/* unshift(): 앞에 삽입 */
/* shift(): 앞에 삭제 */

/** splice() **/
/* splice(n, m): 특정 요소 제거, n부터 시작, m은 개수 */
// let arr = [1, 2, 3, 4, 5];
// arr.splice(1, 2); //index 1부터 2개 제거
// console.log(arr);

/* splice(n, m, x): 특정 요소 제거 후 추가, n부터 시작, m은 개수, x는 추가할 요소 */
// let arr = [1, 2, 3, 4, 5];
// arr.splice(1, 3, 100, 200); //index 1부터 2개 제거
// console.log(arr);

/* splice(): 삭제된 요소를 반환 */
// let arr = [1, 2, 3, 4, 5];
// console.log(arr.splice(1, 2)); //[2, 3]


/** slice(n, m): n부터 m-1까지 반환 **/
// let arr = [1, 2, 3, 4, 5];
// console.log(arr.slice(1, 4)); //[2, 3, 4]
// let arr2 = [1, 2, 3, 4, 5];
// console.log(arr2.slice()); //[1, 2, 3, 4, 5]

/** concat(arr2, arr3, ...): 합쳐서 새 배열 생성 **/
// let arr = [1, 2];
// console.log(arr.concat([3, 4])); //[1, 2, 3, 4]
// console.log(arr.concat([3, 4,], [5, 6])); //[1, 2, 3, 4, 5, 6]
// console.log(arr.concat([3, 4,], 5, 6)); //[1, 2, 3, 4, 5, 6]

/** forEach(fn) **/
// let users = ['Mike', 'Tom', 'Jane'];
// users.forEach((name, index, arr) => {
//     console.log(`${index}: ${name}`);
// });

/** indexOf()와 lastIndexOf() **/
// let arr = [1, 2, 3, 4, 5, 1, 2, 3];
// console.log(arr.indexOf(3)); //2
// console.log(arr.indexOf(3, 3)); //7
// console.log(arr.lastIndexOf(3)); //7

/** includes(): 포함 여부 확인 **/
// let arr = [1, 2, 3];
// console.log(arr.includes(2)); //true
// console.log(arr.includes(8)); //false

/** find(fn)와 findIndex(fn): 첫번째 true 값만 반환 후 끝, 없으면 undefined 반환 **/
// let arr = [1, 2, 3, 4, 5]; //2
// let arr = [1, 3, 5]; //undefined
// let result = arr.find(item => item % 2 === 0);
// console.log(result);

/* find() 예제 */
// let userList = [
//     {name: 'Mike', age: 30},
//     {name: 'Jane', age: 27},
//     {name: 'Tom', age: 10},
// ]

// let findUser = userList.find(user => (user.age < 19) ? true : false);
// let findUserIndex = userList.findIndex(user => (user.age < 19) ? true : false);
// console.log(findUser); // {name: 'Tom', age: 10}
// console.log(findUserIndex); // 2


/** filter(fn): 만족하는 모든 요소를 배열로 반환 **/
// let arr = [1, 2, 3, 4, 5, 6];
// let odd = arr.filter(number => number % 2 !== 0);
// console.log(odd);


/** reverse(): 역순 재정렬 **/
// let arr = [1, 2, 3, 4, 5];
// console.log(arr.reverse()); // [5, 4, 3, 2, 1]


/** map(fn): 함수를 받아 특정 기능 수행 후 새로운 배열 반환 **/
// let userList = [
//     {name: 'Mike', age: 30},
//     {name: 'Jane', age: 27},
//     {name: 'Tom', age: 10},
// ]

// let newUserList = userList.map((user, index) => (
//     Object.assign({}, user, {
//         id: index + 1,
//         isAdult: user.age > 19
//     })
// ));

// console.log(newUserList);


/** join(): Array -> String **/
// let arr = ['안녕', '나는', '철수야'];
// let result = arr.join(', '); //default 구분자는 ',' 이고 설정 가능
// console.log(typeof result); // String
// console.log(result); // 안녕,나는,철수야

/** split(): String -> Array **/
// const users = 'Mike, Jane, Tom, Tony';
// let result = users.split(', ');
// console.log(result);

/** isArray(): 배열인지 확인 **/
// let user = {
//     name: 'Mike',
//     age: 30
// }
// let userList = ['Mike', 'Tom', 'Jane'];
// console.log(Array.isArray(user)); // false
// console.log(Array.isArray(userList)); // true


/** sort(fn): 배열 정렬, 배열 자체가 변경되니 주의 **/
// let arr = [1, 5, 4, 2, 3]; // [1, 2, 3, 4, 5]
// let arr = ['b', 'e', 'd', 'c', 'a']; // ['a', 'b', 'c', 'd', 'e']
// let arr = [27, 8, 5, 13];
// arr.sort((n1, n2) => n1 - n2);
// console.log(arr);


/** reduce(): 인수로 함수를 받음
 * (누적 계산값, 현재값) => { return 계산값 };
 */
// let arr = [1, 2, 3, 4, 5];
/* 방법1. for 사용 */
// let total = 0;
// arr.forEach(num => total += num);
// console.log(total);

/* 방법2. reduce() 사용*/
// let total = arr.reduce((prev, curr) => prev + curr, 0); //초기값 설정 가능
// console.log(total);

/* 예제 */
// let userList = [
//     {name: 'Mike', age: 30},
//     {name: 'Tom', age: 10},
//     {name: 'Jane', age: 27},
//     {name: 'Sue', age: 26},
//     {name: 'Harry', age: 42},
//     {name: 'Steve', age: 60},
// ];

// let adultList = userList.reduce((prev, curr) => {
//     if (curr.age > 19) prev.push(curr);
//     return prev;
// }, []);
// console.log(adultList);

// let totalAge = userList.reduce((prev, curr) => prev + curr.age, 0);
// console.log(totalAge);

// let userNameLength3List = userList.reduce((prev, curr) => {
//     if (curr.name.length === 3) {
//         prev.push(curr);
//     }
//     return prev;
// }, []);
// console.log(userNameLength3List);


/** 구조 분해 할당: 배열, 객체 속성을 분해하여 그 값을 변수에 담을 수 있게 하는 표현식 **/
/* 예제 1 */
// let [x, y] = [1, 2];
// console.log(x, y); //1 2

/* 예제 2 */
// let users = ['Mike', 'Tom', 'Jane'];
// let [user1, user2, user3] = users;
// console.log(user1);
// console.log(user2);
// console.log(user3);

/* 예제 3 */
// let str = 'Mike-Tom-Jane';
// let [user1, user2, user3] = str.split('-');
// console.log(user1, user2, user3);


/* 예제 4 */
// let [a, b, c] = [1, 2]; //c는 undefined 발생
// console.log(a, b, c);

// let [x = 3, y = 4, z = 5] = [1, 2]; //기본값 적용
// console.log(x, y, z);


/* 예제 5 (일부 반환값 무시) */
// let [user1, , user2] = ['Mike', 'Tom', 'Jane', 'Tony'];
// console.log(user1, user2) //Mike Jane

/* 예제 6 (바꿔치기) */
// let a = 1;
// let b = 2;
// let tmp = a;
// a = b;
// b = tmp;
// console.log(a, b); // 2 1 (방법1. 임시 변수 사용)

// [a, b] = [b, a];
// console.log(a, b); // 1 2 (방법2. 구조 분해 할당 사용)


/** 객체 구조 분해 **/
// let user = {name: 'Mike', age: 30}

/* 예제 1 (기본 사용법) */
// let {name, age} = user;
// let {age, name} = user; // 순서가 바뀌어도 동일하게 동작
// console.log(name, age); // Mike 30

/* 예제 2 (새로운 변수명 할당) */
// let {name: userName, age: userAge} = user; // 새로운 변수명으로 사용 가능
// console.log(userName, userAge);

/* 예제 3 (기본값 할당) */
// let person = {name: 'Mike', age: 30, gender: 'female'}
// let {name, age, gender = 'male'} = person;
// console.log(gender); //female


/** 나머지 매개변수 (화살표 함수에서는 사용 불가능) **/
/* 예시 */
// function showName(name) {
//     console.log(name);
// }

/* 방법1. arguments 사용
* - 함수로 넘어온 모든 인자에 접근
* - 함수내 이용 가능한 지역 변수
* - length / index
* - Array 형태의 객체
* - 배열 내장 메서드 X
*/

// function showName(name) {
//     console.log(arguments.length);
//
//     for (let argument of arguments) {
//         console.log(argument);
//     }
// }


/* 방법2. 나머지 매개변수 사용 */
// function showName(...names) {
//     console.log(names);
// }

/* 출력 결과 */
// showName('Mike');
// showName('Mike', 'Tom');
// showName();


/* 예제 1. 넘어온 모든 인자의 합을 구하라. */
// function add(...numbers) {
//     return numbers.reduce((prev, curr) => prev + curr);
// }

// console.log(add(1, 2, 3));
// console.log(add(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

/* 예제 2. user 객체를 만들어주는 생성자 함수를 만들어라. */
// function User(name, age, ...skills) {
//     this.name = name;
//     this.age = age;
//     this.skills = skills;
// }

// console.log(new User('Mike', 30, 'html', 'css'));
// console.log(new User('Tom', 20, 'Java', 'Spring'));
// console.log(new User('Jane', 10, 'English'));

/** 전개 구문 **/
/* 배열 */
// let arr1 = [1, 2, 3];
// let arr2 = [4, 5, 6];
// let result = [0, ...arr1, ...arr2, 7, 8, 9];
// console.log(result);

/* 객체 */
// let user =  {name: 'Mike'}
// let mike = {...user, age: 30}
// console.log(mike);

/** 전개 구문 (복제) **/
// let arr = [1, 2, 3];
// let arr2 = [...arr];
// arr2[0] = 4;
// console.log(arr, arr2); //[1, 2, 3] [4, 2, 3]

// let user = {name: 'Mike', age: 30}
// let user2 = {...user}
// user2.name = 'Tom';
// console.log(user, user2) // { name: 'Mike', age: 30 } { name: 'Tom', age: 30 }


/* 예제1. arr1을 [4,5,6,1,2,3]으로 만들어라. */
// let arr1 = [1, 2, 3];
// let arr2 = [4, 5, 6];
// arr1 = [...arr2, ...arr1];
// console.log(arr1);

/* 예제2. 기존 user에 info와 skills를 추가하라. 단 skills는 dev, lang을 추가해야 한다. */
// let user = {name: 'Mike'};
// let info = {age: 30};
// let dev = ['JS', 'React'];
// let lang = ['Korean', 'English'];

// user = {...user, ...info, skills: [...dev, ...lang]};
// console.log(user);

/***** Closure: 함수와 렉시컬 환경의 조합 *****/
// function makeAdder(x) {
//     return function (y) {
//         return x + y;
//     };
// }

/* 함수 생성 당시 외부 변수를 기억하고 생성 이후에도 계속해서 접근 가능 */
// let add3 = makeAdder(3);
// console.log(add3(2));

// let add10 = makeAdder(10);
// console.log(add10(5)); //15
// console.log(add3(1)); //4


/* 예제 */
/*
function makeCounter() {
    let num = 0;

    return function () {
        return num++;
    };
}

let counter = makeCounter();

console.log(counter()); // 0
console.log(counter()); // 1
console.log(counter()); // 2
*/

/***** setTimeout: 일정 시간이 지난 후 함수 실행 *****/
/* 방법 1 */
// function fn() {
//     console.log(3);
// }
// setTimeout(fn, 3000);

/* 방법 2 (내부 함수) */
// setTimeout(function () {
//     console.log(3)
// }, 3000);

/* 방법 3 (화살표 함수) */
// setTimeout(() => console.log(3), 3000);


/* 방법 4 (인자가 필요한 경우) */
// setTimeout((...names) => names.forEach(name => console.log(name)), 3000, 'Mike', 'Tom', 'Jane');

/* 방법 5 (clearTimeout) */
// let tId = setTimeout((...names) => names.forEach(name => console.log(name)), 3000, 'Mike', 'Tom', 'Jane');
// clearTimeout(tId);

/***** setInterval: 일정 시간 간격으로 함수 반복 *****/
/* 방법 1 */
/*
let num = 0;
function showName() {
    console.log(`${++num}초`);
    if (num > 5) clearInterval(tId);
}

let tId = setInterval(showName, 1000);
*/

/** setTimeout과 setInterval 주의사항 **/
/* delay가 0이여도 후순위로 밀린다. -> 모든 스크립트 실행 후 스케줄 함수가 실행되기 때문이다. */
// setTimeout(() => console.log(2), 0);
// console.log(1);


/***** call, apply, bind (함수 호출 방식과 관계없이 this 지정 가능) *****/
/*
const mike = {
    name: 'Mike',
}

const tom = {
    name: 'Tom',
}

function showThisName() {
    console.log(this.name);
}

showThisName(); // undefined
showThisName.call(mike); // Mike
showThisName.call(tom); // Tom

function update(birthday, occupation) {
    this.birthday = birthday;
    this.occupatition = occupation;
}
*/

/** call: 모든 함수에서 사용 가능하며, this를 특정값으로 지정 가능 **/
/*
update.call(mike, 1999, 'singer');
console.log(mike);

update.call(tom, 2002, 'teacher');
console.log(tom);
*/

/** apply: call과 유사, 함수 매개변수를 배열로 받는다. (차이점) **/
/*
update.apply(mike, [1999, 'singer']);
console.log(mike);

update.apply(tom, [2002, 'teacher']);
console.log(tom);
*/

/* 예제 (call, apply) */
/*
const nums = [3, 10, 1, 6, 4];
console.log(Math.min.call(null, ...nums)); // 1
console.log(Math.max.apply(null, nums)); // 10
*/

/** bind: 함수의 this 값을 영구히 변경 가능 **/
/* 예제 1 */
/*
const mike = {
    name: 'Mike',
}

function update(birthday, occupation) {
    this.birthday = birthday;
    this.occupation = occupation;
}

let updateMike = update.bind(mike);
updateMike(1995, 'developer');
console.log(mike);
*/

/* 예제 2 */
/*
const user = {
    name: 'Mike',
    showName: function () {
        console.log(`hello, ${this.name}`);
    },
};

user.showName();

let fn = user.showName;
fn(); // hello, undefined
fn.call(user); // hello, Mike
fn.apply(user); // hello, Mike

let bindFn = fn.bind(user);
bindFn(); //hello, Mike
*/

/***** 상속, 프로토타입 *****/

/*** 프로토타입 ***/
/* hasOwnProperty()는 프로토타입에 의해 제공되는 메소드 */
// const user = {
//     name: 'Mike'
// };

// console.log(user.name); //Mike
// console.log(user.hasOwnProperty('name')); //true
// console.log(user.hasOwnProperty('age')); //false

/* hasOwnProperty 재정의 */
// const newUser = {
//     name: 'Jane',
//     hasOwnProperty: function() {
//         console.log('haha');
//     }
// }

// console.log(newUser.name); //Jane
// newUser.hasOwnProperty(); //haha

/*** 상속 ***/
/* 예제 1 */
/*
const car = {
    wheels: 4,
    drive() {
        console.log('drive...');
    }
}

const bmw = {
    color: 'red',
    navigation: 1,
};

const benz = {
    color: 'black',
};

const audi = {
    color: 'blue',
};

bmw.__proto__ = car;
benz.__proto__ = car;
audi.__proto__ = car;

const x5 = {
    color: 'white',
    name: 'x5'
};

x5.__proto__ = bmw;

// console.log(x5, `휠 개수: ${x5.wheels}`, `네비게이션 수: ${x5.navigation}`);
// x5.drive()

for (p in x5) {
    if (x5.hasOwnProperty(p)) console.log('O', p);
    else console.log('X', p);
}

console.log(Object.keys(x5));
console.log(Object.values(x5));
*/

/* 예제 2 */

// 방법 1 (__proto__ 활용 -> 만든 객체마다 설정 필요)
/*
const car = {
    wheels: 4,
    drive() {
        console.log('drive...');
    }
};

const bmw = function (color) {
    this.color = color;
};

const x5 = new bmw('red');
const z4 = new bmw('black');

x5.__proto__ = car;
z4.__proto__ = car;

console.log(x5, z4);
*/

//방법 2 (prototype 활용 -> 생성자에 적용하여 중복 제거 용이)
/*
const bmw = function (color) {
    this.color = color;
};

// constructor === true
// bmw.prototype.wheels = 4;
// bmw.prototype.drive = () => console.log('drive...');

// constructor === false
bmw.prototype = {
    constructor: bmw, // 이를 추가해줘야 constructor === true가 된다.
    wheels: 4,
    drive() {
        console.log('drive...');
    },
    navigation: 1,
    stop() {
        console.log('stop!');
    }
}

const x5 = new bmw('red');
const z4 = new bmw('black');

x5.drive();
x5.stop()
console.log(z4.wheels, z4.navigation);

/!* instanceof: 인스턴스 여부 확인 *!/
console.log(z4 instanceof bmw);

/!* 생성자 여부 확인 *!/
console.log(z4.constructor === bmw);
*/

/* 예제 3 (생성자 함수에서 클로저 활용) */
// 이 코드는 계속해서 object의 color가 변경될 위험 존재
// const bmw = function (color) {
//     this.color = color;
// };
// const x5 = new bmw('red');

// const bmw = function (color) {
//     const c = color;
//     this.getColor = function () {
//         console.log(c);
//     };
// };

// let x5 = new bmw('red');
// x5.getColor();

/***** 클래스 *****/
/* 생성자 함수 방식 */
/*
const User = function (name, age) {
    //constructor가 함수 형태
    this.name = name;
    this.age = age;
    // this.showName = function () {
    //     console.log(this.name);
    // };
};

User.prototype.showName = function () {
    console.log(this.name);
};

console.log(new User('Mike', 30));
console.log(User('Mike', 30)); // undefined

//name, age, showName 출력
for (const p in new User('Mike', 30)) {
    console.log(p);
}
*/

/* 클래스 방식 */
/*
class User2 {
    //constructor가 class라고 명시
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    //class 사용 시 함수는 prototype에 저장된다.
    showName() {
        console.log(this.name);
    }
}

console.log(new User2('Tom', 20));
// console.log(User2('Tom', 20)); // TypeError 발생

//name, age 출력
for (const p in new User2('Tom', 20)) {
    console.log(p);
}
*/


/*** 클래스에서의 상속 (extends) ***/
/*
class Car {
    constructor(color) {
        this.color = color;
        this.wheels = 4;
    }
    drive() {
        console.log('drive...');
    }
    stop() {
        console.log('stop!');
    }
}

class BMW extends Car {
    constructor(color) {
        super(color);
        this.navigation = 1;
    }
    park() {
        console.log("park");
    }
    //메소드 오버라이딩
    stop() {
        super.stop(); //부모 메소드 그대로 호출
        console.log('OFF');
    }
}

let z4 = new BMW('blue');
z4.drive();
z4.stop();
z4.park();
console.log(`wheels: ${z4.wheels}, color: ${z4.color}, navigation: ${z4.navigation}`);
*/

/***** promise *****/
/* resolve: 성공, reject: 실패 */
/*
// const pr = new Promise((resolve, reject) => {
//     setTimeout(() => {
//         resolve('OK')
//     }, 1000);
// });

const pr = new Promise((resolve, reject) => {
    setTimeout(() => {
        reject(new Error('error...'));
    }, 1000);
});

pr.then(
    function (result) {
        console.log(`${result}. 가지러 간다.`);
    },
).catch(
    function (error) {
        console.log('다시 주문하세요.');
    }
).finally(
    function () {
        console.log('--- 주문 끝 ---');
    }
);
*/

/* callback 함수 활용 */
/*
const f1 = callback => {
    setTimeout(function () {
        console.log('1번 주문 완료');
        callback();
    }, 1000);
};

const f2 = callback => {
    setTimeout(function () {
        console.log('2번 주문 완료');
        callback();
    }, 3000);
};

const f3 = callback => {
    setTimeout(function () {
        console.log('3번 주문 완료');
        callback();
    }, 2000);
};

console.log('시작');
f1(function () {
    f2(function () {
        f3(function () {
            console.log('끝');
        });
    });
});
*/

/* promise 활용 */
/*
const f1 = () => {
    return new Promise((res, rej) => {
        setTimeout(() => res('1번 주문 완료'), 1000);
    });
};

const f2 = (message) => {
    console.log(message);
    return new Promise((res, rej) => {
        setTimeout(() => res('2번 주문 완료'), 3000);
        // setTimeout(() => rej('XXX'), 3000);
    });
};

const f3 = (message) => {
    console.log(message);
    return new Promise((res, rej) => {
        setTimeout(() => res('3번 주문 완료'), 2000);
    });
};

//promise 체이닝
console.time('시작');
f1()
    .then(res => f2(res))
    .then(res => f3(res))
    .then(res => console.log(res))
    .catch(console.log)
    .finally(() => console.timeEnd('시작'));


//promise.all -> rej가 없다면 다 보여주고, rej가 있다면 다 안보여준다.
console.time('time');
Promise.all([f1(), f2(), f3()])
    .then((res) => {
        console.log(res);
        console.timeEnd('time');
    });

//promise.race -> 가장 먼저 끝나는 작업 반환 후 종료
console.time('time');
Promise.race([f1(), f2(), f3()])
    .then((res) => {
        console.log(res);
        console.timeEnd('time');
    });
*/

/***** async, await *****/

/*** async: async가 붙으면 항상 Promise 반환 ***/
/*
async function getName() {
    // return 'Mike';
    // return Promise.resolve('Tom');
    throw new Error('error...');
}

getName()
    .then(name => console.log(name))
    .catch(err => console.log(err));
*/

/*** await: async 함수가 종료될 때까지 대기 후 진행 (async 함수에서만 사용 가능) ***/
/* 예제 1 */
/*
function getName(name) {
    return new Promise((res, rej) => {
        setTimeout(() => {
            res(name);
        }, 1000);
    });
}

async function showName() {
    let result = await getName('Mike');
    console.log(result)
}

console.log('시작');
showName();
*/

/* 예제 2 */

/*
const f1 = () => {
    return new Promise((res, rej) => {
        setTimeout(() => res('1번 주문 완료'), 1000);
    });
};

const f2 = (message) => {
    console.log(message);
    return new Promise((res, rej) => {
        setTimeout(() => res('2번 주문 완료'), 3000);
        // setTimeout(() => rej(new Error('XXX')), 3000);
    });
};

const f3 = (message) => {
    console.log(message);
    return new Promise((res, rej) => {
        setTimeout(() => res('3번 주문 완료'), 2000);
    });
};

async function order() {
    try {
        //방법 1
        // let result1 = await f1();
        // let result2 = await f2(result1);
        // let result3 = await f3(result2);
        // console.log(result3);

        //방법2
        let result = await Promise.all([f1(), f2(), f3()]);
        console.log(result);
    } catch (e) {
        console.log(e)
    }
    console.log('종료');
}

console.log('시작')
order();
*/

/***** Generator: 함수의 실행을 중간에 멈췄다가 재개할 수 있는 기능 *****/
/*
function* fn() {
    try {
        console.log(1);
        yield 1;
        console.log(2);
        yield 2;
        console.log(3);
        console.log(4);
        yield 3;
        return 'finish';
    } catch (e) {
        console.log(e);
    }
}
*/

/** next() **/
/*
let a = fn();
console.log(a.next()); // { value: 1, done: false }
console.log(a.next()); // { value: 2, done: false }
console.log(a.next()); // { value: 3, done: false }
console.log(a.next()); // { value: 'finish', done: true }
*/

/** return() **/
/*
let a = fn();
console.log(a.next()); // { value: 1, done: false }
console.log(a.return('END')); // { value: 'END', done: true }
*/

/** throw() **/
/*
let a = fn();
console.log(a.next()); // { value: 1, done: false }
console.log(a.throw(new Error('error..'))); // Error 로그 발생 후 { value: undefined, done: true }
*/

/** iterable
 * - Symbol.iterator 메서드 존재
 * - iterator 반환 필요
 * - for of 를 통해 반복 가능
 */
/*
const arr = [1, 2, 3, 4, 5];

const it = arr[Symbol.iterator]();
console.log(it.next()); // { value: 1, done: false }
console.log(it.next()); // { value: 2, done: false }
console.log(it.next()); // { value: 3, done: false }
console.log(it.next()); // { value: 4, done: false }
console.log(it.next()); // { value: 5, done: false }
console.log(it.next()); // { value: undefined, done: true }

for (const number of arr) {
    console.log(number);
}
*/

/** iterator
 * - next 메서드를 가짐
 * - next 메서드는 value와 done 속성을 가진 객체 반환
 * - 작업 종료 시 done은 true가 된다.
 */
/*
function* fn() {
    yield 4;
    yield 5;
    yield 6;
}

const a = fn();
console.log(a[Symbol.iterator]() === a); //Generator와 Iterator는 동일
*/

/*
const str = 'hello'; //iterable
const xx = str[Symbol.iterator]();
for (const s of xx) {
    console.log(s);
}
*/

/*
//Generator는 next()에 인수를 전달할 수 있다.
function* fn() {
    const num1 = yield '첫번째 숫자 입력';
    console.log(num1);

    const num2 = yield '두번째 숫자 입력';
    console.log(num2);

    return num1 + num2;
}

const a = fn();

console.log(a.next());
console.log(a.next(2));
console.log(a.next(4));
*/

/*
//Generator를 통해 필요한 시점까지 계산을 미룰 수 있다. (break 없이 while(true) 사용 가능)
function* fn() {
    let index = 0;
    while (true) {
        yield index++;
    }
}

const a = fn();

console.log(a.next());
console.log(a.next());
console.log(a.next());
console.log(a.next());
console.log(a.next());
*/


//두 Generator 사용 (yield* 사용)
/*
function* gen1() {
    yield 'W';
    yield 'o';
    yield 'r';
    yield 'l';
    yield 'd';
}

function* gen2() {
    yield 'Hello,';
    yield* gen1();
    yield '!';
}

console.log(...gen2());
*/
