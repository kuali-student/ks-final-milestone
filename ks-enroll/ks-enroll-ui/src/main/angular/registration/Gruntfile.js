// Generated on 2014-02-03 using generator-angular 0.7.1
'use strict';

// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// use this if you want to recursively match all subfolders:
// 'test/spec/**/*.js'

module.exports = function (grunt) {

    // Load grunt tasks automatically
    require('load-grunt-tasks')(grunt);

    // Time how long tasks take. Can help when optimizing build times
    require('time-grunt')(grunt);

    // Define the configuration for all the tasks
    grunt.initConfig({

        bowercopy: {
            options: {
                srcPrefix: 'app/bower_components',
                destPrefix: 'dist/bower_components',
                report: false
            },
            build: {
                src: 'bootstrap/dist/**/*'
            }
        },

        // Project settings
        yeoman:{
            // configurable paths
            app:require('./bower.json').appPath || 'app',
            dist:'dist'
        },

        less:{
            development:{
                options:{
                    compress:true,
                    yuicompress:true,
                    optimization:2
                },
                files:{
                    // target.css file: source.less file
                    '.tmp/styles/main.css':'<%= yeoman.app %>/styles/less/main.less'
                }
            }
        },

        // Watches files for changes and runs tasks based on the changed files
        watch:{
            js:{
                files:[
                    '<%= yeoman.app %>/scripts/{,*/}*.js',
                    '<%= yeoman.app %>/components/{,*/}*.js',
                    '!<%= yeoman.app %>/components/{,*/}*.spec.js', // Any .js files in /scripts/ but exclude .spec.js files in /components/.
                    '<%= yeoman.app %>/json/{,*/}*.json'
                ],
                tasks:['newer:jshint:all'],
                options:{
                    livereload:true
                }
            },
            jsTest:{
                files:[
                    'karma.conf.js',
                    'test/spec/{,*/}*.js',
                    'test/mock/{,*/}*',
                    '<%= yeoman.app %>/components/{,*/}*.spec.js'
                ],
                tasks:['newer:jshint:test', 'karma']
            },
            styles:{
                files:['<%= yeoman.app %>/styles/**/*.less', '<%= yeoman.app %>/components/{,*/}*.less'],
                tasks:['less', 'newer:copy:styles'],
                options:{
                    nospawn:true,
                    livereload:true
                }
            },
            gruntfile:{
                files:['Gruntfile.js']
            },
            livereload:{
                options:{
                    livereload:'<%= connect.options.livereload %>'
                },
                files:[
                    '<%= yeoman.app %>/{,*/}*.html',
                    '<%= yeoman.app %>/components/{,*/}*.html',
                    '.tmp/styles/{,*/}*.css',
                    '<%= yeoman.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
                ]
            }
        },

        // The actual grunt server settings
        connect:{
            options:{
                port:9000,
                // Change this to '0.0.0.0' to access the server from outside.
                hostname:'localhost',
                livereload:35729
            },
            livereload:{
                options:{
                    open:true,
                    base:[
                        '.tmp',
                        '<%= yeoman.app %>'
                    ],
                    middleware: function (connect, options) {
                        if (!Array.isArray(options.base)) {
                            options.base = [options.base];
                        }

                        // Setup the proxy
                        var middlewares = [require('grunt-connect-proxy/lib/utils').proxyRequest];

                        // Serve static files.
                        options.base.forEach(function(base) {
                            middlewares.push(connect.static(base));
                        });

                        // Make directory browse-able.
                        var directory = options.directory || options.base[options.base.length - 1];
                        middlewares.push(connect.directory(directory));

                        return middlewares;
                    }

                }
            },
            test:{
                options:{
                    port:9001,
                    base:[
                        '.tmp',
                        'test',
                        '<%= yeoman.app %>'
                    ]
                }
            },
            dist:{
                options:{
                    base:'<%= yeoman.dist %>'
                }
            },
            server: {
                proxies: [
                    {
                        context: '/ks-with-rice-bundled-dev',
                        host: '127.0.0.1',
                        port: '8081',
                        changeOrigin: true,
                        headers: {
                            'Access-Control-Allow-Origin':'http://127.0.0.1:9000',
                            'Access-Control-Allow-Methods':'POST, GET, OPTIONS, DELETE, PUT',
                            'Access-Control-Allow-Header':'Content-Type'
                        }
                    }
                ]
            }
        },

        // Make sure code styles are up to par and there are no obvious mistakes
        jshint:{
            options:{
                jshintrc:'.jshintrc',
                reporter:require('jshint-stylish')
            },
            all:[
                'Gruntfile.js',
                '<%= yeoman.app %>/scripts/{,*/}*.js',
                '<%= yeoman.app %>/components/{,*/}*.js',
                '!<%= yeoman.app %>/components/{,*/}*.spec.js' // Exclude the tests in /components/
            ],
            test:{
                options:{
                    jshintrc:'test/.jshintrc'
                },
                src:['test/spec/{,*/}*.js', '<%= yeoman.app %>/components/{,*/}*.spec.js']
            }
        },

        // Empties folders to start fresh
        clean:{
            dist:{
                files:[
                    {
                        dot:true,
                        src:[
                            '.tmp',
                            '<%= yeoman.dist %>/*',
                            '!<%= yeoman.dist %>/.git*'
                        ]
                    }
                ]
            },
            server:'.tmp',
            deploy:{
                src:'../../../../../../ks-core/ks-common/ks-common-web/src/main/webapp/registration/',
                options:{
                    force:true
                }
            }
        },

        // Add vendor prefixed styles
        autoprefixer:{
            options:{
                browsers:['last 5 versions']
            },
            dist:{
                files:[
                    {
                        expand:true,
                        cwd:'.tmp/styles/',
                        src:'{,*/}*.css',
                        dest:'.tmp/styles/'
                    }
                ]
            }
        },

        // Automatically inject Bower components into the app
        'bower-install':{
            app:{
                html:'<%= yeoman.app %>/index.html',
                ignorePath:'<%= yeoman.app %>/'
            }
        },


        // Renames files for browser caching purposes
        rev:{
            dist:{
                files:{
                    src:[
                        '<%= yeoman.dist %>/scripts/{,*/}*.js',
                        '<%= yeoman.dist %>/components/{,*/}*.js',
                        '!<%= yeoman.dist %>/components/{,*/}*.spec.js', // Exclude the tests in /components/
                        '<%= yeoman.dist %>/styles/{,*/}*.css',
                        '<%= yeoman.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
                        '<%= yeoman.dist %>/styles/fonts/*'
                    ]
                }
            }
        },

        // Reads HTML for usemin blocks to enable smart builds that automatically
        // concat, minify and revision files. Creates configurations in memory so
        // additional tasks can operate on them
        useminPrepare:{
            html:'<%= yeoman.app %>/index.html',
            options:{
                dest:'<%= yeoman.dist %>'
            }
        },

        // Performs rewrites based on rev and the useminPrepare configuration
        usemin:{
            html:['<%= yeoman.dist %>/{,*/}*.html'],
            css:['<%= yeoman.dist %>/styles/{,*/}*.css'],
            options:{
                assetsDirs:['<%= yeoman.dist %>', '<%= yeoman.dist%>/images']
            }
        },

        // The following *-min tasks produce minified files in the dist folder
        imagemin:{
            dist:{
                files:[
                    {
                        expand:true,
                        cwd:'<%= yeoman.app %>/images',
                        src:'{,*/}*.{png,jpg,jpeg,gif}',
                        dest:'<%= yeoman.dist %>/images'
                    }
                ]
            }
        },
        svgmin:{
            dist:{
                files:[
                    {
                        expand:true,
                        cwd:'<%= yeoman.app %>/images',
                        src:'{,*/}*.svg',
                        dest:'<%= yeoman.dist %>/images'
                    }
                ]
            }
        },
        htmlmin:{
            dist:{
                options:{
                    collapseWhitespace:true,
                    collapseBooleanAttributes:true,
                    removeCommentsFromCDATA:true,
                    removeOptionalTags:true
                },
                files:[
                    {
                        expand:true,
                        cwd:'<%= yeoman.dist %>',
                        src:['*.html', '*.jsp', 'partials/{,*/}*.html', 'components/{,*/}*.html'],
                        dest:'<%= yeoman.dist %>'
                    }
                ]
            }
        },

        // Allow the use of non-minsafe AngularJS files. Automatically makes it
        // minsafe compatible so Uglify does not destroy the ng references
        ngmin:{
            dist:{
                files:[
                    {
                        expand:true,
                        cwd:'.tmp/concat/scripts',
                        src:'*.js',
                        dest:'.tmp/concat/scripts'
                    }
                ]
            }
        },

        // Replace Google CDN references
        cdnify:{
            dist:{
                html:['<%= yeoman.dist %>/*.html']
            }
        },

        // Copies remaining files to places other tasks can use
        copy:{
            dist:{
                files:[
                    {
                        expand:true,
                        dot:true,
                        cwd:'<%= yeoman.app %>',
                        dest:'<%= yeoman.dist %>',
                        src:[
                            '*.{ico,png,txt}',
                            '.htaccess',
                            '*.html',
                            'partials/{,*/}*.html',
                            'components/{,*/}*.html',
                            'bower_components/**/*',
                            'images/{,*/}*.{webp}',
                            'fonts/*',
                            'json/*.json'
                        ]
                    },
                    {
                        expand:true,
                        cwd:'.tmp/images',
                        dest:'<%= yeoman.dist %>/images',
                        src:['generated/*']
                    },
                    {
                        expand:true,
                        cwd:'.tmp',
                        dest:'<%= yeoman.dist %>',
                        src:['*.jsp']
                    }
                ]
            },
            styles:{
                expand:true,
                cwd:'<%= yeoman.app %>/styles',
                dest:'.tmp/styles/',
                src:'{,*/}*.css'
            },
            createJsp: {
                src: '<%= yeoman.dist %>/index.html',
                dest: '<%= yeoman.dist %>/index.jsp',
                options: {
                    process: function (content) {
                        return content +
                            '<script>' +
                            '\'use strict\'; ' +
                            'angular.module(\'configuration\').value(\'APP_URL\',\'${ConfigProperties.application.url}/services/\'); ' +

                            // Feature toggle overrides
                            'angular.module(\'regCartApp\').config([\'$provide\', function($provide) { ' +
                            '$provide.decorator(\'FEATURE_TOGGLES\', function($delegate) { $delegate.learningPlan = false; return $delegate; }); ' +
                            '}]); ' +
                            '</script>';
                    }
                }
            },
            deploy:{
                expand:true,
                cwd:'<%= yeoman.dist %>',
                src:['**/*','!bower_components/**','!index.html'],
                dest:'../../../../../../ks-core/ks-common/ks-common-web/src/main/webapp/registration/'

            }
        },

        // Run some tasks in parallel to speed up the build process
        concurrent:{
            server:[
                'copy:styles'
            ],
            test:[
                'copy:styles'
            ],
            dist:[
                'copy:styles',
                'imagemin',
                'svgmin'
            ]
        },

        // By default, your `index.html`'s <!-- Usemin block --> will take care of
        // minification. These next options are pre-configured if you do not wish
        // to use the Usemin blocks.
        // cssmin: {
        //   dist: {
        //     files: {
        //       '<%= yeoman.dist %>/styles/main.css': [
        //         '.tmp/styles/{,*/}*.css',
        //         '<%= yeoman.app %>/styles/{,*/}*.css'
        //       ]
        //     }
        //   }
        // },
        // uglify: {
        //   dist: {
        //     files: {
        //       '<%= yeoman.dist %>/scripts/scripts.js': [
        //         '<%= yeoman.dist %>/scripts/scripts.js'
        //       ]
        //     }
        //   }
        // },
        // concat: {
        //   dist: {}
        // },

        // Test settings
        karma:{
            unit:{
                configFile:'karma.conf.js',
                singleRun:true
            }
        },

        //minify json files
        'json-minify': {
            build: {
                files: 'dist/json/*.json'
            }
        },

        //Grunt check dependencies to make sure NPM dependencies are up to date
        checkDependencies: {
            this:{
                options: {
                    install: true,
                    verbose: true
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-connect-proxy');
    grunt.loadNpmTasks('grunt-json-minify');
    grunt.loadNpmTasks('grunt-bowercopy');

    grunt.registerTask('serve', function (target) {
        if (target === 'dist') {
            return grunt.task.run(['build', 'connect:dist:keepalive']);
        }

        grunt.task.run([
            'clean:server',
            'bower-install',
            'less',
            'concurrent:server',
            'autoprefixer',
            'configureProxies:server',
            'connect:livereload',
            'watch'
        ]);
    });

    grunt.registerTask('server', function () {
        grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
        grunt.task.run(['serve']);
    });

    grunt.registerTask('test', [
        'clean:server',
        'concurrent:test',
        'autoprefixer',
        'connect:test',
        'karma'
    ]);

    grunt.registerTask('build', [
        'checkDependencies',
        'bowercopy',
        'bower-install',
        'karma',
        'clean:dist',
        'less',
        'useminPrepare',
        'concurrent:dist',
        'autoprefixer',
        'concat',
        'ngmin',
        'copy:dist',
        'cdnify',
        'cssmin',
        'uglify',
        'rev',
        'usemin',
        'htmlmin',
        'copy:createJsp',
        'json-minify',
        'clean:deploy',
        'copy:deploy'
    ]);

    grunt.registerTask('default', [
        'newer:jshint',
        'test',
        'build'
    ]);
};
