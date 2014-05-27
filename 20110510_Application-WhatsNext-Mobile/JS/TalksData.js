/**
 * 
 */
Ext.regModel('Talks', {
	hasMany: {
        model: 'Speaker',
        name: 'speakers'
    },
	fields: ['title', 'day', 'time', 'description', 'speaker']
});

whatsnext.TalkStore = new Ext.data.Store({
	model  : 'Talks',
	
    getGroupString : function(record) {
        return record.get('time');
    },
    data: [{
    	title: 'Enterprise Applications in 2011: Challenges in Development and Deployment, and Spring\'s response',
    	day: '1',
    	time: '10:00am', 
    	description: '<p>Enterprise application developers face a new set of challenges building applications that integrate with social media, can be accessed from a rich set of device types, can work with both relational and non-relational data stores, can scale in response to large fluctuations in demand, and can be deployed to a diverse range of environments including cloud/platform-as-a-service (PaaS).</p><p>At the same time, enterprises are seeking to dramatically reduce the time to market for new features and applications. Companies are looking to platform-as-a-service based offerings - both within the enterprise and external - to address these needs. The dynamic environment of a PaaS places a new set of requirements on the middleware components that support applications running there.</p><p>In this session, Adrian Colyer will describe advances being made in the Spring Framework and associated Spring projects to meet the needs of enterprise application development in 2011. We will also discuss the need for dynamically configurable, horizontally scalable middleware services to meet the needs of cloud and PaaS based applications, and give examples from the VMware vFabric Services of how this can be achieved. Finally we will also look at deployment options for Spring applications in 2011, covering the VMforce and Google App Engine collaborations.</p>',
    	speaker: 'colyer'
    }, {
    	title: 'Introducing Orion: Embracing the Web for Software Development Tooling',
    	day: '1',
    	time: '12:00pm', 
    	description: 'Eclipse is embarking on a journey towards web-based development tooling with a new effort called \'Orion\'. The goal of Orion is to build developer tooling that works in the browser, at web scale. This talk will demo the current state of Orion, and explain the vision of moving software development to the web as a web experience, by enabling open tool integration through HTTP and REST, JSON, OAuth, OpenID, and others. The idea is to exploit internet design principles throughout, instead of trying to bring existing desktop IDE concepts to the browser. See http://wiki.eclipse.org/Orion for more information about Orion.',
    	speaker: 'bokowski'
    }, {
    	title: 'Towards the Essence of Programming',
    	day: '1',
    	time: '2:00pm', 
    	description: 'What is the essence of programming? How close do popular languages such as Java bring you towards that essence? In this session, we\'ll discuss Clojure, a language for the Java Virtual Machine that builds on a 40+ year history of Lisp: the programming language that\'s also a programming language toolkit. Clojure combines the best features of Lisp with the breadth and power of the Java Virtual Machine and the Java infrastructure; it allows you to build expressive, beautiful code ... without sacrificing any of Java\'s processing power. Once you embrace Clojure\'s odd (to our eyes) syntax, you\'ll find that its interoperability with Java, its pragmatic approach to functional programming, and its advanced concurrency capabilities are all easily approachable. Clojure turns traditional object-oriented programming on its head: separating data from the functions that operate on that data, with the end result being more concise, more performant, more testable, more readable code: code that gets to the essence of programming.',
    	speaker: 'ship'
    }, {
    	title: 'Do you really get memory?',
    	day: '1',
    	time: '3:15pm', 
    	description: 'That\'s a stupid question, isn\'t it? Memory is just where you keep things. Nevertheless the majority of of performance problems I have seen were in the end connected to memory issues. In modern computers memory is a complex multi-layered thing affected by the hardware, operating system and the JVM. And considering that it\'s shared among multiple CPUs we get a lot of classical distributed problems without an actual network anywhere in sight. You should attend this talk if any of the following questions give you pause: <br\><ul><li>How slow is memory?</li><li>What does volatile really do?</li><li>How pagefaults happen and what do they mean?</li><li>Why swapfile and Java don\'t work together well?</li><li>Why GC doesn\'t scale well (so far)?</li><li>What good alternatives to Java Memory Model are there?</li></ul>To answer those questions I\'ll build a simple CPU model in Java to illustrate some of the concepts and do live simulations for the rest. As a bonus I\'ll also talk about some improvements in the modern hardware and what it could mean for the next generation JVM.',
    	speaker: 'kabanov'
    }, {
    	title: 'SQLFabric - Scalable SQL instead of NoSQL',
    	day: '1',
    	time: '4:30pm', 
    	description: '<p>There is quite a bit of buzz these days on "NoSQL" databases - essentially distributed data stores that offer scalability and high levels of availability through data partitioning and relaxing the consistency semantics in traditional SQL databases. The lack of transactions and support for SQL (which we believe is a rich, easily understood query model) has been a problem for many to adopt these solutions.<p/><p>This talk presents, SQLFabric, a distributed SQL data management solution that melds Apache Derby (borrowing SQL drivers, parsing and some aspects of the engine) and an object data grid (GemFire) to offer a horizontally scalable, memory oriented data management system where developers can continue to use SQL. We focus on new primitives that extend the well known SQL Data definition syntax for data partitioning and replication strategies but leaving the "select" and data manipulation part of SQL intact so it only minimally intrusive to your application.</p>',
    	speaker: 'ramnarayan'
    }, {
    	title: 'HTML5 WebSockets : the Web Communication revolution, making the impossible possible',
    	day: '1',
    	time: '5:45pm', 
    	description: '<p>HTML5 WebSockets is rapidly becoming the standard for better connecting business data with Web-based clients, and also for Web and Cloud enabling parts of the business that perhaps today are not taking advantage of these trends. However the vision for WebSockets is vastly bigger than this. What today constitutes the best choice for delivering low-latency, real time data to Rich Internet Applications is now also proving to be the new standard communication technology for all Web interaction going forward. With the limitations of HTTP lifted, what sorts of applications can now be built? What new ways can you best connect your business with your customers over the Web, Cloud or Mobile with low latency and high scale?</p><p>Come to this session to hear about how WebSocket technology is delivering on the dreams of many large organisations as they better serve their customers demands for business data over the Web, and hear about some of the revolutionary things that were once deemed impossible, made possible.</p>',
    	speaker: 'drysdale'
    }, {
    	title: 'Multi-Platform Messaging with RabbitMQ',
    	day: '2',
    	time: '11:30am', 
    	description: '<p>With the increasing popularity of both cloud computing and polyglot programming, more and more developers are working in environments that are both multi-language and multi-platform. Of course, with many languages and many different deployment contexts, communication between system components can be problematic. Efficient communication between components requires agreement on payload formats, communication protocols and, for developer sanity, sophisticated language bindings. Thankfully, messaging systems such as RabbitMQ provide the perfect solution. RabbitMQ is a popular, open-source, multi-protocol messaging broker with bindings for many popular languages.</p><p>In this talk, Rob Harrop, a senior RabbitMQ team member, will explore some of the options that RabbitMQ provides for multi-platform messaging. Attendees will learn how to access RabbitMQ from a variety of languages including Java, Python, Ruby and Erlang, using different protocols including AMQP and STOMP, and from different environments, including in-browser messaging using WebSockets.</p>',
    	speaker: 'harrop'
    }, {
    	title: 'Service Decoupling in Carrier-Class Architectures',
    	day: '2',
    	time: '1:30pm', 
    	description: '<p>Building highly scalable systems means compromise. That compromise can take many forms, but one common and useful compromise is synchronicity. It is unrealistic (today) to transcode an uploaded video into all supported video codecs during the upload process. Instead, it is typical to upload the video in one step and inform the user that transcoding is happening "behind the scenes." This is a simple example of service decoupling. Extremely large architectures can require service decoupling on a micro transaction level. Anytime the outcome of an job happens at some arbitrary time in the future many challenges arise.<p/><p>In this session we\'ll discuss the pros and cons of decoupling services and frame the engineering decision: "should I decouple?" We will also discuss basic instrumenation required to successfully operate a highly decoupled architecture.</p>',
    	speaker: 'schlossnagle'
    }, {
    	title: 'Taking Continuous Integration to the next level with Jenkins',
    	day: '2',
    	time: '2:45pm', 
    	description: '<p>Continuous Integration (CI) has been widely adopted over the past several years, and many development teams now embrace regular automated builds and tests into their development model. And Jenkins, a very popular open-source CI server, has helped people achieve this. At the same time, beyond those that we consider "basics", we are discovering that there\'s a lot of emerging trends in our industry that mesh very well with CI, and sophisticated users are starting to take advantage of those. Therefore, in this talk, true to the name of the conference, I\'ll discuss what\'s next in CI.<p/><p>One of such trends is the adoption of virtualization and cloud platforms, which brings us substantial automation on things that we couldn\'t automate before, and this works very well with CI in multiple levels. The saturation of single system performance and shift to horizontal scaling also creates a demand for a system like Jenkins, which lets you utilize a larger number of computers effortlessly. Then there\'s the rise of distributed version control system, which allows CI servers to act in ways that further offload developers, not to mention the rise of quality analysis software which helps your software development from another angle.</p><p>Those emerging frontiers of CI would hopefully convince you why you should invest in automation in software development!</p>',
    	speaker: 'kawaguchi'
    }, {
    	title: 'Akka: Simpler Scalability, Fault-Tolerance, Concurrency & Remoting through Actors',
    	day: '2',
    	time: '4:00pm', 
    	description: 'We believe that writing correct concurrent, fault-tolerant and scalable applications is too hard. Most of the time it\'s because we are using the wrong tools and the wrong level of abstraction. Akka is here to change that. Akka is using the Actor Model together with Software Transactional Memory (STM) to raise the abstraction level and provide a better platform to build correct concurrent and scalable applications. For fault-tolerance it adopts the "Let it crash" model which have been used with great success in the Telecom industry to build applications that self-heal, systems that never stop. Akka\'s Remote Actors, together with the Clustering module, provides the abstraction for transparent distribution, adaptive automatic load-balancing and cluster rebalancing, replication and is the basis for truly scalable and fault-tolerant applications. In this talk you will learn what Akka is, how it can be used to solve hard problems and the ideas behind its design and implementation. Akka is available at http://akka.io',
    	speaker: 'boner'
    }, {
    	title: 'Architecting user-experiences',
    	day: '2',
    	time: '6:30pm', 
    	description: 'With the rise of a wide range of Internet connected devices, a new class of application is emerging to work across multiple kinds of devices. Developers are now faced with new challenges to provide the most engaging user experiences on any screen. New device input methods like touch and gestures require developers to rethink interaction models. Screen size constraints also require developers to optimize real estate usage. With so many different mediums for delivering rich Internet applications code, reuse becomes incredibly important. Micha&eumll Chaize will present how Java developers can leverage the new Flex 4.5 framework to solve this actual challenge. One codebase and one language to target Android, iOS and PlayBook devices.',
    	speaker: 'chaize'
    }
    ]
});