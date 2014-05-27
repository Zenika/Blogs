/**
 * 
 */
Ext.regModel('Speaker', {
	hasMany: {
        model: 'Talks',
        name: 'talks'
    },
	fields : [ 'id', 'firstName', 'lastName', 'photo', 'position', 'description', 'twitter' ]
});

whatsnext.SpeakerStore = new Ext.data.Store({
	model : 'Speaker',
	sorters : 'lastName',

	getGroupString : function(record) {
		return record.get('lastName')[0];
	},

	data : [ {
		id: 'banon',
		firstName : 'Shay',
		lastName : 'Banon',
		photo : 'images/shay_banon.gif',
		position : 'Founder, ElasticSearch',
		description : 'Founder of ElasticSearch, an open-source, distributed, RESTful, Search Engine. Previously, Shay was Director of Technology at GigaSpaces Technologies, a leading provider of a new generation of application platforms for Java and .NET environments. Shay also founded in 2004 the Compass project, an open-source Java Search Engine library built on top of Lucene.',
		twitter: 'kimchy'
	}, {
		id: 'colyer',
		firstName : 'Adrian',
		lastName : 'Colyer',
		photo : 'images/adrian_colyer.jpg',
		position: 'CTO, SpringSource / a division of VMware',
		description: 'Adrian Colyer is the Chief Technology Officer of SpringSource, a division of VMware. SpringSource is leading or active in a wide range of open source projects including the Spring Framework and related Spring projects, Groovy, Grails, RabbitMQ, and a number of Apache and Eclipse projects including Tomcat, Virgo, Gemini, AspectJ, and AJDT. Adrian is a key member of the SpringSource team setting strategy and direction for VMware\'s Cloud Application Platform - which combines the Spring programming model and core runtimes with the vFabric platform services for applications, messaging, and data. Adrian has a long-standing interest in software design and modularity. He led the AspectJ project at Eclipse.org, and founded the AJDT and Spring Dynamic Modules projects. In 2004 Adrian was recognised by MIT Technology Review as one of the Top 100 "young innovators" in the world. Adrian has also served on a number of industry groups including the Aspect-Oriented Software Association Steering Committee, the OSGi Enterprise Expert Group, and the Eclipse Architecture Council.',
		twitter: 'adriancolyer'
	}, {
		id: 'gafter',
		firstName : 'Neal',
		lastName : 'Gafter',
		photo : 'images/neal_gafter.jpg',
		position: 'Partner Architect, Microsoft',
		description: 'Neal Gafter was a primary designer and implementer of the Java SE 4 and 5 language enhancements, and his Java Closures implementation won an OpenJDK Innovator\'s Challenge award. He continues to kibitz on the language changes in progress for SE 7 and 8. Neal previously worked on Google\'s online Calendar. He was a member of the C++ Standards Committee and led the development of C and C++ compilers at Sun Microsystems, Microtec Research, and Texas Instruments. Today, Neal works for Microsoft on .NET platform languages. Neal is coauthor of "Java Puzzlers: Traps, Pitfalls, and Corner Cases" (Addison Wesley, 2005). He holds a Ph.D. in computer science from the University of Rochester.',
		twitter: 'gafter'
	}, {
		id: 'bokowski',
		firstName: 'Boris',
		lastName: 'Bokowski',
		photo: 'images/boris_bokowski.jpg',
		position: 'Technical Lead Eclipse Platform UI, IBM ',
		description: 'Boris Bokowski is a software developer working on Eclipse for IBM in Ottawa, Canada. He leads the Eclipse Platform UI team, and is an elected committer representative on the Eclipse Board of Directors. Boris is heavily involved in the new Orion effort at Eclipse, which is bringing software development to the web as a web experience. He is a member of the knights of the lambda calculus, has built a Prusa Mendel 3D printer, and received a PhD from Freie Universitat Berlin.',
		twitter: 'bokowski'
	}, {
		id: 'harrop',
		firstName: 'Rob',
		lastName: 'Harrop',
		photo: 'images/rob_harrop.jpg',
		position: 'Lead Engineer, SpringSource / a division of VMware',
		description: 'As Lead Engineer of SpringSource dm Server, Rob is driving SpringSource\'s enterprise middleware product line and ensuring that the company continues to deliver high-performance, highly scalable enterprise soutions. With a thorough knowledge of both Java and .NET, Rob has successfully deployed projects across both platforms. Rob is the author of five books, including Pro Spring, a widely acclaimed, comprehensive resource on the Spring Framework. Rob is a member of the JCP and is involved in the JSR-255 Expert Group for JMX 2.0. Rob is an experienced, highly-sought after, technical speaker who can communicate complex topics in a way that any developer can understand. Over the past 3-4 years, Rob has also presented at JavaOne, QCon, AOSD, The Spring Experience, SpringONE, OSCon, and OreDev on a variety of topics to rave reviews.',
		twitter: 'robertharrop'
	}, {
		id: 'boner',
		firstName: 'Jonas',
		lastName: 'Bon&eacute;r',
		photo: 'images/jonas_boner.jpg',
		position: 'Founder & CEO, Scalable Solutions AB',
		description: 'Jonas Bon&eacute;r is a programmer, teacher, mentor, speaker and author who spends most of his time consulting, hacking on open source as well as lecturing and speaking at developer conferences world-wide. He has worked at Terracotta, the JRockit JVM at BEA and is an active contributor to the Open Source community; most notably created the Akka Project, AspectWerkz Aspect-Oriented Programming (AOP) framework, committer to the Terracotta JVM clustering technology and been part of the Eclipse AspectJ team.',
		twitter: 'jboner'
	}, {
		id: 'drysdale',
		firstName: 'Brad',
		lastName: 'Drysdale',
		photo: 'images/brad_drysdale.jpg',
		position: 'Director of Technology EMEA, Kaazing Corporation',
		description: 'Brad Drysdale is the Technical Director in EMEA for Kaazing, a software company enhancing the way business and customers communicate across the Web using the new HTML5 WebSocket standard. Brad works closely with customers to architect and deploy the best possible web communication solutions. He is a keen technical evangelist and company spokesperson at various seminars and industry events. Before joining Kaazing, Brad was Technical Director for Azul Systems in EMEA, working with large customers to realise the benefits of virtualised, high-scale, network-attached Java computing. Brad also was the EMEA technical evangelist for Azul and spoke at several Java developer and industry analyst events. Prior to Azul, Brad managed a team of pre-sales professionals at BEA Systems in Australia, and was also the company\'s technical spokesperson, spending a lot of time with customers, industry analysts and at marketing events extolling the virtues of J2EE, SOA and standards-based infrastructure. Brad has also worked at Openwave and Netscape Communications in various pre-sales and technical evangelist roles. He is a proficient speaker and tries his best to describe new and revolutionary technologies in a way easily understandable by audiences ranging from very technical to more business focused. Brad founded and runs the London HTML5 User Group and is a regular speaker at other local technical meet ups. ',
		twitter: ''
	}, {
		id: 'kabanov',
		firstName: 'Jevgeni',
		lastName: 'Kabanov',
		photo: 'images/jevgeni_kabanov.jpg',
		position: 'CTO, ZeroTurnaround',
		description: 'Jevgeni Kabanov is the founder and CTO of ZeroTurnaround, a development tools company that focuses on productivity. Before that he worked as the R&D director of Webmedia, Ltd, the largest custom software development company in the Baltics. As part of the effort to reduce development time tunraround he wrote the prototype of the ZeroTurnaround flagship product, JRebel, a class reloading JVM plugin. Jevgeni has been speaking at international conferences for over 5 years, including JavaPolis/Devoxx, JavaZone, JAOO, QCon, TSSJS, JFokus and so on. He also has an active research interest in programming languages, types and virtual machines, publishing several papers on topics ranging from category theoretical notions to typesafe Java DSLs. Besides the commercial products made for ZeroTurnaround, Jevgeni is a co-founder of two open-source projects -- Aranea and Squill. Aranea is a web development and integration platform based on strong object-oriented principles. Squill is a typesafe internal DSL for constructing and executing SQL queries.',
		twitter: 'ekabanov'
	}, {
		id: 'kawaguchi',
		firstName: 'Kohsuke',
		lastName: 'Kawaguchi',
		photo: 'images/kohsuke_kawaguchi.jpg',
		position: 'Jenkins Project Lead Developer, CloudBees',
		description: 'I\'m Kohsuke Kawaguchi. I\'m a software engineer who enjoys writing code and solving problems. I have been working on a large number of open-source projects. I am probably best known as the creator of Jenkins (Hudson), a continuous integration server. My projects span many different areas of the technology, but my main interest is around developer tools, XML, and web services in Java. I have worked for Sun Microsystems, Inc. for about 8 years, and then briefly worked for Oracle as Sun was acquired by Oracle in 2010. I founded InfraDNA for business around Hudson in 2010 April, which since then joined forces with CloudBees.',
		twitter: 'kohsukekawa'
	}, {
		id: 'ship',
		firstName: 'Howard',
		lastName: 'Lewis Ship',
		photo: 'images/howard_lewis_ship.jpg',
		position: 'President, TWD Consulting, Inc.',
		description: 'Howard Lewis Ship is the creator and lead developer for the Apache Tapestry project, and is a noted expert on Java framework design and developer productivity. He has over twenty years of full-time software development under his belt, with over ten years of Java. He cut his teeth writing customer support software for Stratus Computer, but eventually traded PL/1 for Objective-C and NeXTSTEP before settling into Java. Howard is respected in the Java community as an expert on web application development, dependency injection, Java meta-programming, and developer productivity. He is a frequent speaker at JavaOne, NoFluffJustStuff, ApacheCon and other conferences, and the author of "Tapestry in Action" for Manning (covering Tapestry 3.0). Lately, he\'s been dipping his toes into alternate languages, including Clojure. Howard is an independent consultant, offering Tapestry training, mentoring and project work as well as training in Clojure. He lives in Portland, Oregon with his wife Suzanne, and his son, Jacob.',
		twitter: 'hlship'
	}, {
		id: 'ramnarayan',
		firstName: 'Jags',
		lastName: 'Ramnarayan',
		photo: 'images/jags_ramnarayan.jpg',
		position: 'Chief Architect, GemFire Products, SpringSource / a division of VMware',
		description: 'As the Chief Architect for GemFire product line, Jags is responsible for the technology direction for its high performance distributed data grid and virtualization platform. Jags has represented GemStone Systems in the EJB expert group and the J2EE platform specification. In the past, Jags represented BEA in the W3C SOAP protocol specification, JAXM and other standards. Jags has presented in several conferences in the past on data management, clustering and grid computing (JavaOne, W3C workshops, Java user groups, Open Grid Forum, Platform Grid conference etc). He has over 20 years of experience, a bachelors degree in computer science and a masters degree in management of science and technology.',
		twitter: ''
	}, {
		id: 'schlossnagle',
		firstName: 'Theo',
		lastName: 'Schlossnagle',
		photo: 'images/theo_schlossnagle.gif',
		position: 'CEO and Principal, OmniTI Computer Consulting, Inc.',
		description: 'Theo excels at developing elegant solutions to complicated problems as well as applying emerging technologies to solve everyday problems. As a hands-on executive of the company, he applies his experience and ingenuity to deliver innovative solutions to OmniTI clients. A widely respected industry thought leader, Theo is the author of Scalable Internet Architectures (Sams) and a frequent speaker at worldwide it conferences. He was also the principal architect of the Momentum mta, which is now the flagship product of OmniTI\'s sister company, Message Systems. Born from Theo\'s vision and technical wisdom, this innovation is transforming the email software spectrum. Theo is a member of the IEEE and a senior member of the ACM. He serves on the editoral board of the ACM\'s Queue Magazine.',
		twitter: 'postwait'
	}, {
		id: 'chaize',
		firstName: 'Micha&euml;l ',
		lastName: 'Chaize',
		photo: 'images/michael_chaize.jpg',
		position: 'EMEA Flash Platform Evangelist, Adobe Systems',
		description: '<p>Micha&eumll Chaize is a Flash Platform Evangelist at Adobe where he focuses on Rich Internet Application and Enterprise Integration.<br/>Based in Paris, he works with large accounts that need to understand the benefits of rich user interfaces, leverage the existing back-ends to add a rich presentation layer and measure the impact on the existing IT teams.<br/>He believes that intuitive user experiences in the Enterprise are key to successful developments of effective, efficient, engaging, easy to learn and error free applications.<br/>He loves to promote concepts such as Productivity by design or User Experience oriented architectures. In his previous role, Michael worked as a Technical Sales in France and assumed the promotion of the Flash Platform in front of large audiences of developers and IT managers.<br/>Before joining Adobe, Michael founded a software company and taught RIA languages such as Flex in IT engineering schools.</p>',
		twitter: 'mchaize'
	}
	]
});