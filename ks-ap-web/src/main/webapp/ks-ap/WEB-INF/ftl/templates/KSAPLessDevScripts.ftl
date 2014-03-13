<script>
    /*
    * When in dev mode, this enables live in-browser less-recompiling
    * To have live updates, type this in the js console:
    * less.watch();
    * */
    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "${request.contextPath}/ks-ap/scripts/devmode/live_less_loading.js";
    document.body.appendChild(s);
    //Global variable for less processing in the browser
    var less = {
        env: "development",
        async: false,
        fileAsync: false,
        poll: 1000,
        functions: {},
        dumpLineNumbers: "all"
    };
    //Include less.js processing script for live in-browser processing
    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "${request.contextPath}/ks-ap/scripts/vendor/less-1.7.0.min.js";
    document.body.appendChild(s);
</script>