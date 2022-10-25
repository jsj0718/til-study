$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/ad-banner',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data['ads']);
            createImages(data['ads']);
        }
    });

    function createImages(ads) {
        let strDOM = "";
        for (let i = 0; i < ads.length; i++) {
            let info = ads[i]
            strDOM += '<li><a href="' + info['clickUrl'] + '">';
            strDOM += '<img src="' + info['imgUrl'] + '">';
            strDOM += '<dl><dt>' + info['itemName'] + '</dt></dl>';
            strDOM += '</a></li>';
        }

        // 이미지 컨테이너에 생성한 이미지 패널들을 추가하기
        let $imageContainer = $(".list_box.img_type ul");
        $imageContainer.append(strDOM);
    }
});
