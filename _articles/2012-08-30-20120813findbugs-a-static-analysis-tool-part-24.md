---
ID: 181
post_title: >
  FindBugs, a static analysis tool (part
  2/4)
author: shadjiat
post_date: 2012-08-30 09:30:00
post_excerpt: '<p>In the previous <a href="/index.php?post/2012/08/23/Static-analysis-%28part-1/4%29">article</a>, you have learned some bases in static analysis and how it can be used to improve code quality. Today, I will present FindBugs, one of the most known static analyzers for Java.<br /></p> <p><img src="/public/Billet_0368/findbugs_logo.png" alt="FindBugs" style="display:block; margin:0 auto;" title="FindBugs" /></p>'
layout: post
permalink: http://blog.zenika-offres.com/?p=181
published: true
slide_template:
  - default
---
In the previous <a href="/index.php?post/2012/08/23/Static-analysis-%28part-1/4%29">article</a>, you have learned some bases in static analysis and how it can be used to improve code quality. Today, I will present FindBugs, one of the most known static analyzers for Java.

<!--more-->
<h3><img class=" aligncenter" title="FindBugs" src="/wp-content/uploads/2015/07/findbugs_logo.png" alt="FindBugs" /></h3>
<h3>Introduction</h3>
<a href="http://findbugs.sourceforge.net/">FindBugs</a> is an open source software that looks for bugs in java bytecode using static analysis. It has been developped by William Pugh from the University of Maryland in 2003. Its current version is 2.0.1 and can detect more than 400 programming mistakes in the following categories:
<ul>
	<li>Bad practice</li>
	<li>Correctness</li>
	<li>Internationalization</li>
	<li>Malicious code vulnerability</li>
	<li>Multithread correctness</li>
	<li>Performance</li>
	<li>Security</li>
	<li>Dodgy code</li>
</ul>
<h3>Start guide</h3>
FindBugs can be used in different forms :
<ul>
	<li>Standard tool: GUI, Ant, command line</li>
	<li>Plugins: Eclipse, Maven, Hudson, Intellij, Netbeans</li>
</ul>
I will focus in this part on the Eclipse plugin, there should be no difference though in the results and uses.
<h4>How to install the eclipse plugin</h4>
In your eclipse window menu, select Help =&gt; Install New Software... =&gt; add site :
<ul>
	<li>http://findbugs.cs.umd.edu/eclipse update site for official releases</li>
	<li>http://findbugs.cs.umd.edu/eclipse-candidate update site for candidate releases and official releases</li>
	<li>http://findbugs.cs.umd.edu/eclipse-daily update site for all releases, including developmental ones</li>
</ul>
<h4>How to use it</h4>
Once installed, findbugs can be directly run on your project or class(es) with right-click =&gt; Find Bugs =&gt; Find Bugs.

<a href="/wp-content/uploads/2015/07/run_findbugs_menu.png"><img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.run_findbugs_menu_s.jpg" alt="run_findbugs_menu.png" /></a>

When a bug is found, it can be viewed either by selecting it (the bug icon on the left) or by opening manually the Bug Explorer and Bug Tracker views. The first view present all the bugs found for the given project(s), the second one details each bug.

<a href="/wp-content/uploads/2015/07/test_findbugs_on_project.PNG"><img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.test_findbugs_on_project_s.jpg" alt="test_findbugs_on_project.PNG" /></a>

FindBugs can be "tuned" along with project Properties =&gt; FindBugs or workspace Preferences =&gt; java =&gt; FindBugs.

<a href="/wp-content/uploads/2015/07/findbugs_project_properties.PNG"><img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.findbugs_project_properties_s.jpg" alt="findbugs_project_properties.PNG" /></a>

To work with project properties, you need to enable <em>project specific settings</em>. In the <em>reporter configuration</em> tab menu, you can select which types of bugs are reported. However, they are still enabled. To select specifically the detectors you want to run, you need to go in the <em>detector configuration</em> tab. Other menus enable to create filters for particular classes, and in <em>plugin and misc settings</em>, you can tune how to execute findbugs (extra job) and add external plugin detectors (however this doesn't work very well, the best way is to build the plugin, I will explain this later).
<h3>FindBugs internals</h3>
In this section, you will learn some elements about FindBugs architecture. We'll begin by exploring its source code then see how it works.
<h4>Building from source</h4>
FindBugs source code can be downloaded from <a href="http://code.google.com/p/findbugs/">google code</a>. The svn <a href="http://findbugs.googlecode.com/svn/trunk/">repository</a> contains in particular 3 folders: <em>findbugs</em>, <em>eclipsePlugin</em>, and <em>eclipsePluginTest</em>, which are necessary to build, run and test the eclipse plugin. Note that the folder <em>plugins</em> is also needed to build <em>eclipsePlugin</em> (NoUpdateCheck). Theses projects can be directly imported into the eclipse workspace. To build the findbugs plugin, just run <em>ant build</em> on <em>build.xml</em> (target dist). This will generate the distribution in the folder <em>dist</em> and compile all classes of <em>findbugs</em> et <em>eclipsePlugin</em> in <em>bin_build</em>.

To test and debug your plugin from eclipse, you can create a new Eclipse configuration from Run =&gt; Debug Configuration ... =&gt; Eclipse Application =&gt; New Launch configuration. You can define the workspace location for the new runtime, and in the plug-ins menu, select the plugins to enable. In the list you will see the findbugs plugin in workspace.

<a href="/wp-content/uploads/2015/07/eclipse_debug_configuration.PNG"><img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/.eclipse_debug_configuration_s.jpg" alt="eclipse_debug_configuration.PNG" /></a>

You can know run/debug your plugin in this new Eclipse application.
<h4>Architecture</h4>
As you may see, the findbugs project contains over 1000 java classes in more than 50 packages, it can then be quite difficult at first to get through it. The main package is <em>edu.umd.cs.findbugs</em>: it contains the <em>FindBugs</em> class which is the main engine lauching the analysis, main model classes defining bugs (BugInstance, BugAnnotation) and detectors (Detector). In <em>edu.umd.cs.findbugs.ba</em> and sub-packages are all classes doing static analysis (<em>ba</em> stands for "Bytecode Analysis"). FindBugs uses the BCEL libray to manipulate binary Java class files. All of the implemented detectors can be found in <em>edu.umd.cs.findbugs.detect</em>.

Fundamental classes and interfaces:
<ul>
	<li>FindBugs2: main class (replacing FindBugs), orchestrates the analysis of a project.</li>
	<li>Project: represents the java project to be analysed : application classes (scanned classes), auxiliary classes (referenced in project but not scanned), source directories.</li>
	<li>IAnalysisCache: Interface for registering classes, performing analysis, and caching the results.</li>
	<li>ClassDescriptor / MethodDescriptor: identify the class / method to be analysed.</li>
	<li>Detector/Detector2: Interface implemented by all detectors, visits and inspects classes, then reports all the results at the end.</li>
	<li>ExecutionPlan/AnalysisPass: set the execution plan of findbugs, order detectors in the analysis passes. Defined in findbugs.xml</li>
	<li>BugInstance/BugAnnotation: represent the warning and its description.</li>
	<li>BugReporter: class in charge of reporting bugs (BugInstance).</li>
	<li>BugCollection: collection of BugInstance objects.</li>
</ul>
So, what happens when you execute FindBugs? First, the program reads the project to be analysed and discovers all the application classes. Then it loads the detectors and creates an execution plan defining the detectors to apply, their order through the analysis passes. Finally, it runs the detectors on the application classes following this algorithm:
<pre> for each analysis pass in the execution plan do 	for each application class do 			for each detector in the analysis pass do 				apply the detector to the class 			end for 	end for end for</pre>
The analysis passes can be used to perform interprodecural analysis. Databases are used to relay collected information from a earlier detector to a later one.
<h3>Coming soon</h3>
After learning these basics about Findbugs, you will see in the next post how to write and add custom detectors, the tool's modularity beeing one of its most interesting features.