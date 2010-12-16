#!/usr/bin/env python
import os , sys  , re, glob , csv ,fnmatch

def predicate(fn):
    piecesOfPardir=fn.split(os.path.sep)
    return 'quietriot' in piecesOfPardir and 'model' in piecesOfPardir    

def write(fn, c) :
    fp = open( fn ,'w')
    fp.write(c)
    fp.close() 

def read(fn):
    fp =open(fn, 'r')
    c = fp.readlines()
    fp.close()
    return c
    
def fixId(fn) :
    intId='int id'    
    lines = read( fn)
    nlines =[]
    
    for l in lines:
        nlines.append( re.sub('//(.*)$', '', l))
        
    contentOfFile = os.linesep.join( nlines)
    versionJava = """
    
   private java.util.Date dateCreated ;
   @Temporal(TemporalType.TIMESTAMP) @Column(name="date_created", nullable=false, length=10)
   public Date getDateCreated() { return this.dateCreated; }
   public void setDateCreated(Date dc) { this.dateCreated =dc; }

   
   private java.util.Date dateModified;
   @Temporal(TemporalType.TIMESTAMP) @Column(name="date_modified", nullable=false, length=10)
   public Date getDateModified() { return this.dateModified; }
   public void setDateModified(Date dc) { this.dateModified =dc; }
    
        
    private java.lang.Long version;
     @javax.persistence.Version   public java.lang.Long getVersion() { return version; }
    public void setVersion(java.lang.Long value) { this.version = value; }
    """
    contentOfFile = re.sub(r'package(.*?);',r'package  \1 ;import java.util.*;import javax.persistence.*;',contentOfFile)
    contentOfFile = re.sub(r'public Set<(.*?)>', r' @org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE) public Set<\1> ', contentOfFile)
    contentOfFile = re.sub(r'public class (.*?)',r'@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE) public class \1' ,contentOfFile) 
    contentOfFile = re.sub('int\\s*?id','Integer id',  contentOfFile) 
    contentOfFile = re.sub( 'public int getId' ,'public Integer getId' ,contentOfFile)
    contentOfFile = re.sub('\\s+?', ' ',contentOfFile)
    contentOfFile = re.sub( '@Id.*?@Column' , '@Id    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO) @Column', contentOfFile)    
    if contentOfFile.find('dateModified') < 0 and contentOfFile.find('dateCreated') < 0 : 
        contentOfFile = re.sub( '@Id', '%s %s'%( versionJava, '@Id'), contentOfFile)
    write(fn, contentOfFile)

if __name__ == '__main__':

    listOfFiles = [] 
    for path, dirs, files in os.walk( 'target'):  
        for javaFile in [os.path.abspath(os.path.join(path, filename)) for filename in files if fnmatch.fnmatch(filename, '*.java')]:
            if predicate(javaFile) is True:  
                listOfFiles.append(javaFile)

    print os.linesep.join(listOfFiles)

    for fn in listOfFiles :
        fixId(fn)

        
