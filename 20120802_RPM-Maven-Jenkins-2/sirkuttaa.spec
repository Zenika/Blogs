%define mvn_opts -Dproject.rpm.version=%{version}-%{build_id} \\\
                 -Dproject.rpm.installDirectory=%{buildroot} \\\
                 -Dproject.rpm.appendAssemblyId=false

%define mvn mvn %{mvn_opts}

Name:      sirkuttaa
Version:   1
Release:   %{build_id}
BuildArch: noarch
Summary:   The famous CLI Twitter client
License:   GPLv2+
Group:     Zenika/Blog
Packager:  Zenika
Requires:  java


%description
The ultimate command line experience for Twitter written in Java.


%clean
%{__rm} -rf %{buildroot}


%prep
%{mvn} clean


%build
%{mvn} package


%install
%{__rm} -rf %{buildroot}
%{mvn} verify


%files
%defattr(644,root,root,755)

/usr/lib/sirkuttaa
/usr/lib/sirkuttaa/commons-io-2.3.jar
/usr/lib/sirkuttaa/jackson-core-asl-1.9.7.jar
/usr/lib/sirkuttaa/jackson-mapper-asl-1.9.7.jar
/usr/lib/sirkuttaa/sirkuttaa-1-%{build_id}.jar

%attr(755,root,root) /usr/bin/sirkuttaa

%config(noreplace)  /etc/sirkuttaa/default.properties
%config(noreplace)  /etc/sysconfig/sirkuttaa

