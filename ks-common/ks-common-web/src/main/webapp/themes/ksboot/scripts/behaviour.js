function initAMQListener(principal) {
    var amq = org.activemq.Amq;
    amq.init({
        uri: 'amq',
        logging: true,
        timeout: 30
    });

    amq.addListener('theBrowser', 'org.kuali.student.user.message.' + principal, function(msg) {
        showGrowl(msg.textContent, 'title', 'WARNING');
    });

    amq.startPolling();
}

function removeAMQListener(principal) {
    org.activemq.Amq.removeListener( 'theBrowser', 'org.kuali.student.user.message.' + principal );
}
