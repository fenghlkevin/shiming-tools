/**
 * Created by fengh on 2016/5/17.
 */

Date.prototype.Format = function (fmt) {
    // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4
            - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1)
                ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

Date.prototype.addDays = function (d) {
    this.setDate(this.getDate() + d);
    return this;
};

Date.prototype.addWeeks = function (w) {
    this.addDays(w * 7);
    return this;
};

Date.prototype.addMonths = function (m) {
    var d = this.getDate();
    this.setMonth(this.getMonth() + m);

    if (this.getDate() < d)
        this.setDate(0);
    return this;
};

Date.prototype.addYears = function (y) {
    var m = this.getMonth();
    this.setFullYear(this.getFullYear() + y);

    if (m < this.getMonth()) {
        this.setDate(0);
    }
    return this;
};

//js格式化时间
Date.prototype.toDateStr = function (formatStr) {
    var date = this;
    var timeValues = function () {
    };
    timeValues.prototype = {
        year: function () {
            if (formatStr.indexOf("yyyy") >= 0) {
                return date.getFullYear();
            } else {
                return date.getFullYear().toString().substr(2);
            }
        },
        elseTime: function (val, formatVal) {
            return formatVal >= 0 ? (val < 10 ? "0" + val : val) : (val);
        },
        month: function () {
            return this.elseTime(date.getMonth() + 1, formatStr.indexOf("MM"));
        },
        day: function () {
            return this.elseTime(date.getDate(), formatStr.indexOf("dd"));
        },
        hour: function () {
            return this.elseTime(date.getHours(), formatStr.indexOf("hh"));
        },
        minute: function () {
            return this.elseTime(date.getMinutes(), formatStr.indexOf("mm"));
        },
        second: function () {
            return this.elseTime(date.getSeconds(), formatStr.indexOf("ss"));
        }
    }
    var tV = new timeValues();
    var replaceStr = {
        year: ["yyyy", "yy"],
        month: ["MM", "M"],
        day: ["dd", "d"],
        hour: ["hh", "h"],
        minute: ["mm", "m"],
        second: ["ss", "s"]
    };
    for (var key in replaceStr) {
        formatStr = formatStr.replace(replaceStr[key][0], eval("tV." + key+ "()"));
        formatStr = formatStr.replace(replaceStr[key][1], eval("tV." + key+ "()"));
    }
    return formatStr;
}