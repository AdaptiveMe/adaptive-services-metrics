#!/bin/bash

HOME_DIR=`pwd`
PROJECT_NAME=adaptive-services-metrics-producer-server-1.0
SPRINGBOOTAPP_HOME=${HOME_DIR}/target
SPRINGBOOTAPP_JAR=$SPRINGBOOTAPP_HOME/$PROJECT_NAME.jar
LOG=$SPRINGBOOTAPP_HOME/$PROJECT_NAME.log

pid_of_spring_boot() {
    pgrep -f "java.*${PROJECT_NAME}"
}

start() {
    [ -e "$LOG" ] && cnt=`wc -l "$LOG" | awk '{ print $1 }'` || cnt=1

    echo "Starting ${PROJECT_NAME}... "

    cd "$SPRINGBOOTAPP_HOME"
    nohup java -jar $SPRINGBOOTAPP_JAR >> $LOG 2>&1 &

    while { pid_of_spring_boot > /dev/null ; } &&
        ! { tail -n +$cnt "$LOG" | grep -q ' Started ' ; } ; do
        sleep 1
    done

    pid_of_spring_boot > /dev/null
    RETVAL=$?
}

stop() {
    echo "Stopping $PROJECT_NAME... "

    pid=`pid_of_spring_boot`
    [ -n "$pid" ] && kill $pid
    RETVAL=$?
    cnt=10
    while [ $RETVAL = 0 -a $cnt -gt 0 ] &&
        { pid_of_spring_boot > /dev/null ; } ; do
            sleep 1
            ((cnt--))
    done
}

status() {
    pid=`pid_of_spring_boot`
    if [ -n "$pid" ]; then
        echo "$PROJECT_NAME (pid $pid) is running..."
        return 0
    fi
    echo "$PROJECT_NAME is stopped"
    return 3
}

# See how we were called.
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    restart)
        stop
        start
        ;;
    *)
        echo $"Usage: $0 {start|stop|restart|status}"
        exit 1
esac

exit $RETVAL
