---
ID: 149
post_title: 'Book review: Mastering lambdas'
author: lclaisse
post_date: 2015-02-05 17:30:00
post_excerpt: |
  <p><img src="/public/_Claisse/cover.jpg" alt="cover.jpg" style="float:left; margin: 0 1em 1em 0;" /> <a href="http://www.amazon.fr/Mastering-Lambdas-Programming-Multicore-World/dp/0071829628/ref=sr_1_1_twi_1?s=english-books&amp;ie=UTF8&amp;qid=1422549092&amp;sr=1-1&amp;keywords=mastering+lambdas">Mastering lambdas</a> is the second book I've read about lambda. The first one, that i'll use for comparison, was "Java 8 in action" (which is about Java 8 in general, not only lambdas).</p> <p>This one is written in the same precise style as the very good (and relatively little known) "Java generics and collections", of which Naftalin is a co-author. Some of the content (exception management, performance considerations,...) can't be found anywhere else. On the other hand J8IA is not as well written, but it covers one important technique that "Mastering lambdas" doesn't (using CompletableFutures with Streams).</p> <p>Overall it's a great book. Also because it's so concise, it's not too thick and can be read in a few days.</p> <p>This review is based on the printed version, not the Kindle edition (that i didn't try).</p>
layout: post
permalink: http://blog.zenika-offres.com/?p=149
published: true
slide_template:
  - default
---
<a href="http://www.amazon.fr/Mastering-Lambdas-Programming-Multicore-World/dp/0071829628/ref=sr_1_1_twi_1?s=english-books&amp;ie=UTF8&amp;qid=1422549092&amp;sr=1-1&amp;keywords=mastering+lambdas">Mastering lambdas</a> is the second book I've read about lambda. The first one, that i'll use for comparison, was "Java 8 in action" (which is about Java 8 in general, not only lambdas).

This one is written in the same precise style as the very good (and relatively little known) "Java generics and collections", of which Naftalin is a co-author. Some of the content (exception management, performance considerations,...) can't be found anywhere else. On the other hand J8IA is not as well written, but it covers one important technique that "Mastering lambdas" doesn't (using CompletableFutures with Streams).

Overall it's a great book. Also because it's so concise, it's not too thick and can be read in a few days.

This review is based on the printed version, not the Kindle edition (that i didn't try).

<!--more-->
<h3><img src="/wp-content/uploads/2015/07/cover.jpg" alt="cover.jpg" /></h3>
<h3>CHAPTER 1: Taking Java to the Next Level</h3>
The book begins with the now usual explanation of how in a multi-core context, going from external to internal iteration lets the java runtime utilize new "degrees of freedom", particularly to enable parallelism.
<h4>A structured explanation of the main programming model changes</h4>
<ul>
	<li>From anonymous inner classes to lambdas: All that can logically be inferred by the compiler is introduced step by step</li>
	<li>From collections to streams: The old model of chaining transformations by creating a new Collection at every step is bad from performance (creating a lot of intermediary objects), and also pollutes the code with boilerplate. The solution is to use unix-like pipes and filters, which are lazy and compose into pipelines better than classes.</li>
	<li>From sequential to parallel: Parallelism is explicit, but unobstrusive, using just <code>parallelStream()</code>.</li>
	<li>Lazyness: The intermediate operations don't pull any data from the pipeline's source. Work is only done when it can't be delayed anymore, which is at the pipeline's terminal operation.</li>
</ul>
<h3>CHAPTER 2:The Basics of Java Lambda Expressions</h3>
<h4>The grammar of lambdas</h4>
This chapter goes in more detail into the grammar of lambdas. The compiler's inference engine is well explained, which makes lambdas feel less magical than in the usual presentations. The part on the different kinds of method handles is much better than what I read so far (i didn't get the difference between <em>instance bound</em> and <em>instance unbound</em> references before).
<h4>From basic stuff to more advanced topics</h4>
It goes progressively:
<ol>
	<li>Expression lambdas and statement lambdas</li>
	<li>Differences with anonymous classes (ex: doesn't have to be a new instance every time)</li>
	<li>Syntax, scope, and capture rules (unlike Javascript the value, and the not the variable, is captured)</li>
	<li><code>java.util.function</code>: the starter kit of functional interfaces</li>
	<li>Type inference rules</li>
	<li>Method references kinds: static, instance bound, instance unbound (for those, invocation target is the first lambda argument)</li>
	<li>Detailed rules: checked exceptions handling, overload resolution</li>
</ol>
<h3>CHAPTER 3:Introduction to Streams and Pipelines</h3>
<h4>Goals of pipelining operations</h4>
A pipeline fuses multiple logical operations into one single pass on the data, like with unix pipes. Pipelines are composed of:
<ol>
	<li>a source</li>
	<li>intermediate operations (composed from the API's Stream-&gt;Stream methods)</li>
	<li>a terminal operation</li>
</ol>
The most common of each are listed in this chapter.

An important point is that composing stream operations into a pipeline is only a logical transformation, which doesn't pull any data from the pipeline's source yet.

Another advantage of streams related to lazy processing is <em>short-circuiting</em>: for some operations, if a result is found, the rest can be skipped (ex: if we want to check that a property is true for all elements, we can skip the rest of the stream if we find one element for which it's false).
<h5>One unclear point in this chapter</h5>
"As we saw in §3.1, calling the terminal operation of a pipeline results in the execution of a fusion of its intermediate operations. As a result, the usual debugging technique of stepping through operations is not available for streams<a title="..." href="...">...</a>" For sure, I can place a breakpoint in a lambda (tested with Eclipse and IntelliJ):
<pre class="java code java" style="font-family: inherit;">Stream.<span style="color: #006633;">of</span> <span style="color: #009900;">(</span><span style="color: #0000ff;">"toto"</span>, <span style="color: #0000ff;">"tata"</span>, <span style="color: #0000ff;">"titi"</span><span style="color: #009900;">)</span>.<span style="color: #006633;">map</span> <span style="color: #009900;">(</span>s <span style="color: #339933;">-&gt;</span> <span style="color: #009900;">{</span> 	    <span style="color: #003399;">System</span>.<span style="color: #006633;">out</span>.<span style="color: #006633;">println</span> <span style="color: #009900;">(</span><span style="color: #0000ff;">"STEP 1 Stream: "</span> <span style="color: #339933;">+</span> s<span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #666666; font-style: italic;">//i can stop at this breakpoint</span> 	    <span style="color: #000000; font-weight: bold;">return</span> s<span style="color: #339933;">;</span> 	<span style="color: #009900;">}</span><span style="color: #009900;">)</span>.<span style="color: #006633;">map</span> <span style="color: #009900;">(</span>s <span style="color: #339933;">-&gt;</span> <span style="color: #009900;">{</span> 	    <span style="color: #003399;">System</span>.<span style="color: #006633;">out</span>.<span style="color: #006633;">println</span> <span style="color: #009900;">(</span><span style="color: #0000ff;">"STEP 2 Stream: "</span> <span style="color: #339933;">+</span> s<span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #666666; font-style: italic;">//i can stop at this breakpoint</span> 	    <span style="color: #000000; font-weight: bold;">return</span> s<span style="color: #339933;">;</span> 	<span style="color: #009900;">}</span><span style="color: #009900;">)</span>.<span style="color: #000000; font-weight: bold;">forEach</span> <span style="color: #009900;">(</span>s <span style="color: #339933;">-&gt;</span> <span style="color: #009900;">{</span> 	    <span style="color: #003399;">System</span>.<span style="color: #006633;">out</span>.<span style="color: #006633;">println</span> <span style="color: #009900;">(</span><span style="color: #0000ff;">"STEP 3 Stream: "</span> <span style="color: #339933;">+</span> s<span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #666666; font-style: italic;">//i can stop at this breakpoint</span> 	<span style="color: #009900;">}</span><span style="color: #009900;">)</span> 	<span style="color: #339933;">;</span></pre>
That's one advantage over .NET lambdas, where I can't stop at the same breakpoints (tested with C# 4.5 in Visual Studio). I think the author means that there is no breakpoint corresponding to completion of a single filter of the pipeline, because execution order is not the same as with external iteration. The previous code displays this:
<pre class="java code java" style="font-family: inherit;">STEP <span style="color: #cc66cc;">1</span> Stream<span style="color: #339933;">:</span> toto STEP <span style="color: #cc66cc;">2</span> Stream<span style="color: #339933;">:</span> toto STEP <span style="color: #cc66cc;">3</span> Stream<span style="color: #339933;">:</span> toto STEP <span style="color: #cc66cc;">1</span> Stream<span style="color: #339933;">:</span> tata STEP <span style="color: #cc66cc;">2</span> Stream<span style="color: #339933;">:</span> tata STEP <span style="color: #cc66cc;">3</span> Stream<span style="color: #339933;">:</span> tata STEP <span style="color: #cc66cc;">1</span> Stream<span style="color: #339933;">:</span> titi STEP <span style="color: #cc66cc;">2</span> Stream<span style="color: #339933;">:</span> titi STEP <span style="color: #cc66cc;">3</span> Stream<span style="color: #339933;">:</span> titi</pre>
Whereas the corresponding external iteration approach:
<pre class="java code java" style="font-family: inherit;">List<span style="color: #339933;">&lt;</span>String<span style="color: #339933;">&gt;</span> input <span style="color: #339933;">=</span> <span style="color: #003399;">Arrays</span>.<span style="color: #006633;">asList</span> <span style="color: #009900;">(</span><span style="color: #0000ff;">"toto"</span>, <span style="color: #0000ff;">"tata"</span>, <span style="color: #0000ff;">"titi"</span>&lt;
span style="color: #009900;"&gt;)<span style="color: #339933;">;</span> List<span style="color: #339933;">&lt;</span>String<span style="color: #339933;">&gt;</span> step1 <span style="color: #339933;">=</span> <span style="color: #000000; font-weight: bold;">new</span> ArrayList<span style="color: #339933;">&lt;</span>String<span style="color: #339933;">&gt;</span> <span style="color: #009900;">(</span>input<span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #000000; font-weight: bold;">for</span><span style="color: #009900;">(</span><span style="color: #003399;">String</span> s <span style="color: #339933;">:</span> step1<span style="color: #009900;">)</span> <span style="color: #003399;">System</span>.<span style="color: #006633;">out</span>.<span style="color: #006633;">println</span> <span style="color: #009900;">(</span><span style="color: #0000ff;">"STEP 1 Iterator: "</span> <span style="color: #339933;">+</span> s<span style="color: #009900;">)</span><span style="color: #339933;">;</span> List<span style="color: #339933;">&lt;</span>String<span style="color: #339933;">&gt;</span> step2 <span style="color: #339933;">=</span> <span style="color: #000000; font-weight: bold;">new</span> ArrayList<span style="color: #339933;">&lt;</span>String<span style="color: #339933;">&gt;</span> <span style="color: #009900;">(</span>step1<span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #000000; font-weight: bold;">for</span><span style="color: #009900;">(</span><span style="color: #003399;">String</span> s <span style="color: #339933;">:</span> step1<span style="color: #009900;">)</span> <span style="color: #003399;">System</span>.<span style="color: #006633;">out</span>.<span style="color: #006633;">println</span> <span style="color: #009900;">(</span><span style="color: #0000ff;">"STEP 2 Iterator: "</span> <span style="color: #339933;">+</span> s<span style="color: #009900;">)</span><span style="color: #339933;">;</span> List<span style="color: #339933;">&lt;</span>String<span style="color: #339933;">&gt;</span> step3 <span style="color: #339933;">=</span> <span style="color: #000000; font-weight: bold;">new</span> ArrayList<span style="color: #339933;">&lt;</span>String<span style="color: #339933;">&gt;</span> <span style="color: #009900;">(</span>step2<span style="color: #009900;">)</span><span style="color: #339933;">;</span> <span style="color: #000000; font-weight: bold;">for</span><span style="color: #009900;">(</span><span style="color: #003399;">String</span> s <span style="color: #339933;">:</span> step1<span style="color: #009900;">)</span> <span style="color: #003399;">System</span>.<span style="color: #006633;">out</span>.<span style="color: #006633;">println</span> <span style="color: #009900;">(</span><span style="color: #0000ff;">"STEP 3 Iterator: "</span> <span style="color: #339933;">+</span> s<span style="color: #009900;">)</span><span style="color: #339933;">;</span></pre>
Displays that:
<pre class="java code java" style="font-family: inherit;">STEP <span style="color: #cc66cc;">1</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> toto STEP <span style="color: #cc66cc;">1</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> tata STEP <span style="color: #cc66cc;">1</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> titi STEP <span style="color: #cc66cc;">2</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> toto STEP <span style="color: #cc66cc;">2</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> tata STEP <span style="color: #cc66cc;">2</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> titi STEP <span style="color: #cc66cc;">3</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> toto STEP <span style="color: #cc66cc;">3</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> tata STEP <span style="color: #cc66cc;">3</span> <span style="color: #003399;">Iterator</span><span style="color: #339933;">:</span> titi</pre>
So pipeline execution one element at a time prevents us from mentally slicing execution into individual steps during debug, but it is still possible to follow the steps of processing a single element through the pipeline. This could have been formulated more clearly, unless i'm missing the point (there is a limitation though, you can't see the lambda's outside variables ).
<h4>Pipelines and non-interference</h4>
Finally, the notion of <em>non-interference</em> is introduced: for a pipeline to be parallel-ready, it must have no side-effects, and especially not modify its source.

The next 2 chapters explore the pipeline's "extremities": its end in chapter 4 about collection and reduction, and its beginning in chapter 5 about Stream sources.
<h3>CHAPTER 4: Ending Streams: Collection and Reduction</h3>
<h4>Collectors: the pipeline terminal operation</h4>
A Collector is a pipeline terminal operation. When <code>stream.collect(collector)</code> is called, it accumulates elements into a mutable collection. It may also do a final post-processing operation on that collection, for instance producing an aggregate result.
<h4>The 3 kinds of Collectors</h4>
There are 3 kinds of collectors, starting with the basic out-of-the-box ones and continuing with the API's more and more general extension points:
<h5>The predefined collectors</h5>
They create a stream from a collection source: <code>Collectors.toSet</code>/<code>groupingBy</code>/<code>partitionningBy</code>, ...
<h5>Composed collectors</h5>
The predefined <code>partitionningBy()</code> returns a <code>Map&lt;Boolean,List&lt;T&gt;&gt;</code> But the API can't possibly do that for every kind of end result! So instead it offers a more general <code>collect()</code> overload, to compose a base/upstream collector (<code>groupingBy</code>) with a user-specified downstream collector (<code>Collectors.toList</code>/<code>counting</code>/<code>maxBy</code>/...). The API has similar overloads for other collector factory methods (for instance <code>Collectors.mapping</code>)
<h5>Custom collectors</h5>
They are the most general construction. They are explained just below:
<h4>Creating custom Collectors</h4>
Custom collectors are instantiated by passing all the collector's parts to a factory method like so: <code>Collector.of(supplier, accumulator, combiner, finisher, characteristics)</code>:
<ul>
	<li>the <code>supplier</code> creates the mutable collection</li>
	<li>the <code>accumulator</code> accumulates value into that</li>
	<li>the <code>combiner</code> is used in parallel mode to agregate partial aggregate results</li>
	<li>the <code>finisher</code> applies a final transformation to the mutable collection</li>
	<li>the <code>characteristics</code> are used by the framework (or custom implementations) to decide on possible optimizations.</li>
</ul>
This is more difficult than the chapters before and I had to read it twice, but I think I now have a better mental model of this large API. The part on collectors is concluded by explaining the contract demanded from custom Collector implementations (for instance thread-safety requirements).
<h4>Collector's little brother: Reduction</h4>
The second (and shorter) half of this chapter is about the other major kind of terminal operation, reduction.

Reduction summarizes a stream by an immutable value. Unlike collection, at each step a new temporary immutable value is produced. Reduction is a special case of collection, as it can be implemented by a collector that itself uses a downstream collector (see <em>Composed collectors</em>).

Since the pipeline may be empty by the time reduction is reached, the API provides both an overload of <code>redu
ce()</code> that takes a default value (the reduction's neutral element, like <code>0</code> for <code>+</code>), and another that doesn't take a default value but returns an <code>Optional&lt;R&gt;</code> result.
<h4>In closing</h4>
Collection will probably be used more often than reduction. Both are essential components of the Stream API to understand (but if you understand the more general collection you understand reduction as well).

This is important especially for parallel streams because if done wrong, the terminal operation can be a bottleneck that prevents any parallel speedup.
<h3>CHAPTER 5: Starting Streams: Sources and Spliterators</h3>
After the pipeline's end, this chapter examines the pipeline's beginning (this order is justified by the fact that custom stream initiating is more complex).
<h4>JDK out-of-the-box stream starters</h4>
The easiest way is to use the new API methods that directly provide Streams from sources:
<ul>
	<li>The JDK's collections have been augmented with the <code>Collection.stream()</code> method, which returns a stream whose source is that collection</li>
	<li>The <code>Stream</code> interface has static factory methods: <code>Stream.of(...)</code>, <code>Stream.empty()</code>/<code>Stream.iterate/generate(lambda)</code></li>
	<li>The IO API has been augmented too: <code>BufferedReader.lines()</code> (lazy, unlike eager <code>readAllLines()</code>.</li>
</ul>
One restriction is that file reading is sequential in most (but not all, as we'll see) cases, in which case it can't be parallelized. The java 7 NIO2 API has been augmented too with <code>Files.walk</code>/<code>find</code>/<code>list</code> that take lambdas.
<h4>Custom streams</h4>
To implement custom streams, one must implement the new <code>Spliterator</code> core abstraction. Spliterators are explained much more clearly than in other sources I read so far. Spliterators are strongly linked to the java 7 fork-join framework for recursive decomposition (divide-and-conquer), so this begins with a reminder: considering a recursive task, at each recursive iteration the computation can either do the remaining work sequentially, or fork (then join) parallel subtasks.
<h5>Spliterators</h5>
The name <code>Spliterator</code> evokes this choice: "split" evokes the parallel divide-and-conquer part, while "iterator" evokes the "bottom" sequential iteration.

The 2 essential methods to implement are:
<ul>
	<li><code>tryAdvance(Consumer)</code> implements the "bottom" sequential iteration</li>
	<li><code>trySplit()</code> returns null if further splitting is not worth it, otherwise it returns another Spliterator AND "shortens" the current one so that the two don't overlap</li>
</ul>
Once your custom Spliterator is implemented, use <code>StreamSupport.stream(Spliterator)</code> to instantiate a custom Stream (an example is given at the end of the chapter).
<h4>A touchy subject: exception propagation</h4>
Another difficult topic that is often avoided by authors is exception propagation during stream processing; here an example using <code>IOException</code>/<code>UncheckedIOException</code> (the latter introduced by java 8) is included, illustrating the two alternatives.

The easiest alternative is to "do nothing": with lazy evaluation, the exception will be generated during the terminal operation, when it triggers execution of the exception-generating lambda, and that exception will completely stop the terminal operation.

This is OK in most cases as most errors are not recoverable.

On the other hand some errors ARE recoverable (ex: if one of the files is not readable, this must not stop processing of the other files). In this case recoverable errors must be notified by checked exceptions, thrown from eagerly evaluated operations (in other words: can't use lazyness in this case).
<h4>Example: IO parallelization</h4>
This chapter ends with an original example of a parallel stream that does non-blocking IO. It is constructed from a custom Spliterator that splits a NIO <code>MappedByteBuffer</code> (a native "direct" buffer that maps a file into memory, here in read-only mode).

I liked the fact that it's a rare example of using NIO as an example in a book, and in believable circumstances, and also it really helps to understand how spliterators work; the main point is that to achieve parallel gains in the stream starting stage of stream processing, the stream source must splittable without contention.

This is the case for the example's <code>MappedByteBuffer</code> (because it's loaded once and for all in read-only mode).

This is a necessary but not sufficient condition though - the problem to solve must be intrinsically non-sequential, which is also the case for the example's "line grep" functionnality. As the author says: "Cooperation with Java NIO enables the Stream API to process input from large files with a high parallel speedup."
<h4>In closing</h4>
To quote Naftalin again, "The main strength of the Stream API is that the solutions are straightforward, and respecifying the problem to simulate the different options required only small corresponding changes in the code."
<h3>CHAPTER 6: Stream performance</h3>
<h4>Measure, don't guess</h4>
The first step to improve performance is measuring it, which is often done with a micro-benchmark; after listing some of the most common microbenchmark pitfalls (lack of warmup, dead code elimination, ..), two frameworks that alleviate these problems are introduced (Java Microbenchmark Harness and Google Caliper).
<h4>Factors making parallelism worth it or not</h4>
After the bench harness is ready and performance requirements have been gathered, if the code has been made parallel-ready using Streams correctly, it is time to choose between sequential processing or actual parallel processing; the latter may yield a speedup or not, depending on different factors:
<h5>The execution context</h5>
<ul>
	<li>Can this parallel stream use all the machine's cores, or even all the web server's cores?</li>
</ul>
This is not necessarily the case, and if concurrent requests or other running programs compete for cores the speedup will be much smaller. A possible pointer is setting the common fork-join pool size at VM startup time.
<h5>Task sizes</h5>
<ul>
	<li>The stream's workload is defined as <strong>W = Q(individual task time) * N(number of elements)</strong>.</li>
</ul>
The bigger W, the likelier parallel speedup exceeds parallelization overhead
<ul>
	<li>Stream extremities (Spliterator/Collector) performance: at high Q parallelizing the stream's intermediate operations is worth it, while at low Q splitting by sources and concurrent accumulation by collectors are likely to become bottlenecks</li>
	<li>"More complex intermediate operations will usually involve some I/O or synchronization, resulting in fewer gains from going parallel"</li>
</ul>
<h5>The stream characteristics</h5>
Apart from the runtime context and workload, the stream intrinsic characteristics also have a big impact:
<ul>
	<li><code>SORTED</code></li>
	<li><code>SIZED</code></li>
	<li><code>DISTINCT</code></li>
	<li><code>ORDERED</code> (most important)</li>
</ul>
These characteristics are a product of both the stream's source and modifications by intermediate operations. Some optimizations will be possible or not based on them.
<h5>The kinds of operations performed</h5>
Then some other factors are considered:
<ul>
	<li>Stateful VS stateless operations</li>
	<li>Boxing/Unboxing overhead</li>
	<li>Spliterator and Collector performance in different cases</li>
</ul>
<h4>In closing</h4>
It concludes by saying that Stream creation and collection is not where big parallel speedups are achieved, but can be bottlenecks that need tuning. To achieve parallel speedup, key factors are:
<ul>
	<li>doing most of the work in intermediate operations</li>
	<li>sufficiently high data set size (at least 10_000/100_000 elements) and per-element processing cost (at least 40/200µS per element)</li>
</ul>
Optimiza
tion or not, "<em>investment in writing parallel-ready code will not be wasted, whatever the outcome of your performance analysis. Your code will be better and, when advances in technology change the cost-benefit balance of going parallel, it will—as the name suggests—be ready</em>"
<h4>A few omissions</h4>
So a very good chapter, but with some omissions:
<ul>
	<li>The biggest one is that every call to <code>parallelStream()</code> uses the same singleton <code>ForkJoinPool</code>: <code>ForkJoinPool.commonPool()</code>.</li>
</ul>
This is a big trap imo (that Heinz Kabutz likes talking about), so don't ever do blocking IO or any blocking calls in a parallel stream. The author does insist that parallel streams are for CPU intensive tasks though.
<ul>
	<li>Exception: a more obscure point is that if you call <code>parallelStream()</code> while already within a ForkJoinPool, that pool is used instead of <code>ForkJoinPool.commonPool()</code>.</li>
</ul>
Well it seems to be the case but it's unspecified as far as I know..
<ul>
	<li><code>CompletableFuture</code> is not mentionned.</li>
</ul>
While it is not related to streams, J8IA does talk about it and makes the very important point that: "Conversely, when dealing with concurrency instead of parallelism, or when your main goal is to perform several loosely related tasks on the same CPUs, keeping their cores as busy as possible to maximize the throughput of your application, <a title="..." href="...">...</a>". <code>CompletableFuture</code> is useful for max throughput, while <code>parallelStream()</code> is useful for max performance. Which is more important most of the time? Probably throughput and J8IA shows a possible way to go that direction using Streams and CompletableFutures together.
<h3>CHAPTER 7: API Evolution with Default Methods</h3>
<h4>Reasons for their introduction</h4>
Default methods are linked to lambdas, because they were added to interfaces so that the addition of methods such as <code>Iterable.forEach()</code> is not a breaking change.
<h4>A few implementation patterns</h4>
After presenting their syntax, this chapter lists their common points/differences with abstract classes, and presents some interesting implementation patterns.
<h4>More technical points</h4>
More technical points include:
<ul>
	<li>While lambdas are restricted to implementing Single Abstract Method Interface/FunctionalInterface, within a <code>FunctionalInterface</code>, concrete default methods are legal, and useful for instance as auxiliary methods to compose lambdas (ex: <code>Predicate.and()</code>).</li>
	<li>Inheritance and overload resolution rules</li>
	<li>The reason why they can't be declared final (which can be annoying) is just that the inheritance rule "classes always win over interfaces" prevents it.</li>
	<li>Static interface methods</li>
</ul>
<h3>CHAPTER 8: Conclusion</h3>
Java 8 brings concepts of functional programing, immutability, and lazy evaluation to the java language. Their integration in an old language like java is well done, and they enable more concise, readable, and performant code.
<h3>Overall: Recommended</h3>
A very polished book. My only regret is that it doesn't talk about mixing Streams and CompletableFuture for better throughput.