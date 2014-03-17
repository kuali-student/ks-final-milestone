function initAMQListener(principal) {
    var amq = org.activemq.Amq;
    amq.init({
        uri: 'amq',
        logging: true,
        timeout: 30
    });

    amq.addListener('theBrowser', 'org.kuali.student.user.message.' + principal, function(msg) {
        showGrowl(msg.textContent, '', 'WARNING');
    });

}