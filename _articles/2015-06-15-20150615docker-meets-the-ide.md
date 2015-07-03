---
ID: 185
post_title: Docker meets the IDE
author: mloriedo
post_date: 2015-06-15 23:33:00
post_excerpt: |
  <p>This blog post is about integrating Docker's magic into our IDEs. This will give us the opportunity to introduce a plugin to edit, build and run Docker containers inside Eclipse: <a href="https://github.com/domeide/doclipser">doclipser</a></p> <p><img src="/public/Billet_0571/domeide-color-light.png" alt="domeide" style="display:block; margin:0 auto;" /></p>
layout: post
permalink: http://blog.zenika-offres.com/?p=185
published: true
slide_template:
  - default
---
This blog post is about integrating Docker's magic into our IDEs. This will give us the opportunity to introduce a plugin to edit, build and run Docker containers inside Eclipse: <a href="https://github.com/domeide/doclipser">doclipser</a>

<!--more-->

[caption id="" align="aligncenter" width="350"]<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/domeide-color-light.png" alt="domeide" width="350" height="268" /> Docker[/caption]
<h3>Docker in development environments</h3>
Before going into the details of Docker and IDEs integration, let's argument why we think Docker has to be in our list of essentials development tools.
<h4>Build system</h4>
First of all, Docker allows us to make our <strong>build</strong> <strong>environments</strong> portable, repeatable and isolated. Let's make an example: you need to build a C file using gcc v5.1. All you need to do is run the following command (assuming, of course, that you have Docker installed):
<pre class="bash code bash" style="font-family: inherit;">docker run <span style="color: #c20cb9; font-weight: bold;">gcc</span>:<span style="color: #000000;">5.1</span> <span style="color: #c20cb9; font-weight: bold;">gcc</span> <span style="color: #660033;">-o</span> helloworld helloword.c</pre>
The magic is that you can run it anywhere, you don't have to bother about libraries, conflicts or installing gcc. If it runs on your laptop it will run on the integration server too.
<h4>Runtime environment</h4>
The same apply to <strong>runtime</strong> <strong>environments</strong>. If you need to run your java web application within tomcat 8 you can just use:
<pre class="bash code bash" style="font-family: inherit;">docker run tomcat:<span style="color: #000000;">8</span> <span style="color: #660033;">-v</span> HelloWorld.war:<span style="color: #000000; font-weight: bold;">/</span>usr<span style="color: #000000; font-weight: bold;">/</span>local<span style="color: #000000; font-weight: bold;">/</span>tomcat<span style="color: #000000; font-weight: bold;">/</span>webapps<span style="color: #000000; font-weight: bold;">/</span>Helloworld.war</pre>
Again you don't have to worry about dependencies, platforms configurations or linux distributions differences. It just works.
<h3>Docker and IDEs</h3>
Ok, it should be clear why Docker is useful for development now. And we have illustrated that with a bunch of commands you can use on your favorite shell. And we love our shells. The problem is that a trait of remarkable developers is <a href="http://blog.codinghorror.com/get-me-the-laziest-people-money-can-buy/">inspired laziness</a>. This means that if we are coding inside our favorite IDE we don't want to get outside of it. We want to run containers right from the IDE.

Unfortunately Docker isn't supported by major IDEs right now.

So how would you like to see Docker integrated in your IDE?

<strong>Would you run your IDE inside a container?</strong>
<pre class="bash code bash" style="font-family: inherit;">docker run eclipse</pre>
Naaaaa....That's not the way containers work. It's still too tricky and not portable to run graphical applications inside containers.

<strong>Would you run containers from within your IDE?</strong>

Oh Yeahhh! And with that we would love to see Dockerfile support, Compose yml files support, IDE build systems and runtime environment running inside containers. Let's see the details:
<h4>Dockerfile support</h4>
Dockerfile support should come with syntax highlighting, autocomplete (dockerfile instructions popping out when hitting CTRL+SPACE) and syntax validation (syntax errors should be shown by your IDE before you `docker build`)

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/docker_img_syntax.jpg" alt="docker_img_syntax.jpg" />
<h4>Compose yml file support</h4>
Even more interesting would be Compose yml file support. That would allow to define inter container relation as links and volumes and run multiple containers with one click from your editor. That is cool!

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/compose.png" alt="compose" />
<h4>IDE Build system</h4>
Of course you would need support for running containers from the IDE. Specifically containers that can build your source files. That could be even easier if leveraging Docker language stacks.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/buildsystems.png" alt="buildsystems" />
<h4>IDE Runtime Environments</h4>
And the last feature we want in our IDE is the possibility to launch runtime enviroments inside Docker containers. Right from your IDE.

<img style="display: block; margin: 0 auto;" src="/wp-content/uploads/2015/07/runenv.png" alt="runenv" />
<h3>Introducing doclipser</h3>
With these features in mind we built <a href="https://www.github.com/domeide/doclipser">doclipser</a>. An eclipse plugin to edit, build and run Docker containers.

doclipser has Dockerfile support: syntax highlighting, autocomplete and syntax verification. It still doesn't have Compose yml file support but supports a few Docker commands that allow you to build source files or launch runtime environments.

doclipser is not the only Docker eclipse plugin. There is the great <a href="http://tools.jboss.org/blog/2015-03-30-Eclipse_Docker_Tooling.html">Eclipse Docker Tooling (EDT)</a> too. It's packaged with the Eclipse Linux Tools and maintained by JBoss. Even if they both make it possible to work with Docker from within the IDE, doclipser and EDT have two different approaches:
<ul>
	<li>doclipser focus on editing and running containers through the support of Dockerfiles and, in future releases, Compose yml file</li>
	<li>EDT has a more advanced management of Docker images and containers but lacks the support for Dockerfiles.</li>
</ul>
<center><iframe src="https://player.vimeo.com/video/130819115" width="500" height="463" frameborder="0" allowfullscreen="allowfullscreen"></iframe></center><center><a href="https://vimeo.com/130819115">doclipser demo</a> from <a href="https://vimeo.com/user41114661">Mario Loriedo</a> on <a href="https://vimeo.com">Vimeo</a>.</center>&nbsp;

&nbsp;
<h3>domeide.github.io</h3>
Doclipser isn't the only tool that brings Docker inside your IDE. A few tools already exist and we are building a github page to collect all of them: <a href="https://domeide.github.io">domeide.github.io</a>. We have included:
<ul>
	<li><a href="https://packagecontrol.io/packages/Docker%20Based%20Build%20Systems">Sublime Docker</a></li>
	<li><a href="http://blog.jetbrains.com/idea/2015/03/docker-support-in-intellij-idea-14-1/">IntelliJ IDEA 14.1</a></li>
	<li><a href="http://tools.jboss.org/blog/2015-03-30-Eclipse_Docker_Tooling.html">Eclipse JBoss Tools</a></li>
	<li><a href="https://visualstudiogallery.msdn.microsoft.com/6f638067-027d-4817-bcc7-aa94163338f0">Visual Studio 2015 RC Tools for Docker - Preview extension</a></li>
</ul>
Do you have any tool you want to share ?

—-

<em>This blog post is the transcription of the lighting talk I gave at <a href="http://2015.dotscale.io">dotScale 2015</a></em>