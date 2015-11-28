package lexer;

import java.io.*;
import java.util.*;

public class Lexer {
	public static int line = 1; /* 行号 */
	char peek = ' '; /* 下一个读入字符 */
	Hashtable<String, Word> words = new Hashtable<String, Word>(); /* 符号表 */
	//private Hashtable<Token, String> table = new Hashtable<Token, String>(); /* token集 */
	private List<String> table = new LinkedList<String>();/* token集 */
	private List<String> tokens = new LinkedList<String>(); /* token表 */
	private List<Error> errors = new LinkedList<Error>(); /* 报错表 */
	BufferedReader reader = null;
	private Boolean isEnd = false; /* 是否读取到文件的结尾 */
	//private static int numlimit = 1<<24; /* 数字上限 */
	private static int lengthlimit = 32; /* 标识符长度上限 */
	private static int lengthvalid = 8; /* 标识符有效长度 */
	private static String sep = System.getProperty("line.separator"); /* 系统换行 */
	
 	public Boolean getReaderState() {/* 获取读入状态 */
		return this.isEnd;
	}

	/*public void saveSymbolsTable() throws IOException {  //保存符号类型 
		FileWriter writer = new FileWriter("符号表.txt");
		writer.write("-------->TypeOfCharater<--------" + sep);
		for(Enumeration<Token> e = table.keys();e.hasMoreElements();) { 
			Token token = e.nextElement();
			writer.write("<" + token + ">:<" + table.get(token) + ">" + sep);
		}
		writer.flush();
		writer.close();
	}*/
 	/* 保存存储在table中的 */  
    public void saveSymbolsTable() throws IOException {  
        FileWriter writer = new FileWriter("符号表.txt");  
        writer.write("[符号]          [符号类型信息]\n");  
        writer.write("\r\n");  
          
        for(int i = 0; i < table.size(); ++i) {  
        	//int er = (int)errors.get(i).line;
           // String err = (String)errors.get(i).error;  
            String w = table.get(i);
            /* 写入文件 */  
            writer.write("<"+w + ">	:			<id>"+"\r\n");  
        }     
              
        writer.flush();  
    }  

	public void saveTokens() throws IOException { //保存Tokens 
		FileWriter writer = new FileWriter("Tokens表.txt");
		//writer.write("-------->Tokens<--------" + sep);
		writer.write("[符号]  \n");
		writer.write("\r\n");
		for(String token : tokens){
			writer.write("< " + token + " >" + sep);
		}
		writer.flush();
		writer.close();
	}
    /* 保存Tokens */  
    /*public void saveTokens() throws IOException {  
        FileWriter writer = new FileWriter("Tokens表.txt");  
        writer.write("[符号]  \n");  
        writer.write("\r\n");  
          
        for(int i = 0; i < tokens.size(); ++i) {  
            String tok = (String)tokens.get(i);  
              
             //写入文件   
            writer.write(tok + "\r\n");  
        }     
              
        writer.flush();  
    }  
*/
	/*public void saveErrors() throws IOException { 保存Tokens 
		FileWriter writer = new FileWriter("errors.txt");
		writer.write("-------->Errors<--------" + sep);
		for(String error : errors){
			writer.write( error + sep);
		}
		writer.flush();
		writer.close();
	}*/
	/* 保存 Errors*/
    public void saveErrors() throws IOException{           //	待写
    	FileWriter ew = new FileWriter("errors.txt");  
       // ew.write("[符号]          [符号类型信息]\n");  
        ew.write("\r\n");  
          
        for(int i = 0; i < errors.size(); ++i) {  
        	//int er = (int)errors.get(i).line;
           // String err = (String)errors.get(i).error;  
            Error error = errors.get(i);
            /* 写入文件 */  
            ew.write(error.writeError() + "\r\n");  
        }     
              
        ew.flush();  
    }

	void reserve(Word w) { /* 保存单词到符号表 */
		words.put(w.lexme, w);
	}

	public Lexer() {
		try {	/* 初始化读取文件变量 */
			reader = new BufferedReader(new FileReader("输入.txt"));
		} catch (IOException e) {
			System.out.print(e);
		}

		/* 关键字 */{
			this.reserve(Word.Absolute);
			this.reserve(Word.Abstract);
			this.reserve(Word.Array);
			this.reserve(Word.As);
			this.reserve(Word.Asm);
			this.reserve(Word.Assembler);
			this.reserve(Word.At);
			this.reserve(Word.Automated);
			this.reserve(Word.Begin);
			this.reserve(Word.Case);
			this.reserve(Word.Cdecl);
			this.reserve(Word.Class);
			this.reserve(Word.Const);
			this.reserve(Word.Constructor);
			this.reserve(Word.Contains);
			this.reserve(Word.Default);
			this.reserve(Word.Destructor);
			this.reserve(Word.Dispid);
			this.reserve(Word.Dispinterface);
			this.reserve(Word.Do);
			this.reserve(Word.Downto);
			this.reserve(Word.Dynamic);
			this.reserve(Word.Else);
			this.reserve(Word.End);
			this.reserve(Word.Except);
			this.reserve(Word.Export);
			this.reserve(Word.Exports);
			this.reserve(Word.External);
			this.reserve(Word.Far);
			this.reserve(Word.File);
			this.reserve(Word.Finalization);
			this.reserve(Word.Finally);
			this.reserve(Word.For);
			this.reserve(Word.Forward);
			this.reserve(Word.Function);
			this.reserve(Word.Goto);
			this.reserve(Word.If);
			this.reserve(Word.Implementation);
			this.reserve(Word.Implements);
			this.reserve(Word.In);
			this.reserve(Word.Index);
			this.reserve(Word.Inherited);
			this.reserve(Word.Initialization);
			this.reserve(Word.Inline);
			this.reserve(Word.Interface);
			this.reserve(Word.Is);
			this.reserve(Word.Label);
			this.reserve(Word.Library);
			this.reserve(Word.Message);
			this.reserve(Word.Name);
			this.reserve(Word.Near);
			this.reserve(Word.Nil);
			this.reserve(Word.Nodefault);
			this.reserve(Word.Object);
			this.reserve(Word.Of);
			this.reserve(Word.On);
			this.reserve(Word.Out);
			this.reserve(Word.Overload);
			this.reserve(Word.Override);
			this.reserve(Word.Package);
			this.reserve(Word.Packed);
			this.reserve(Word.Pascal);
			this.reserve(Word.Private);
			this.reserve(Word.Procedure);
			this.reserve(Word.Program);
			this.reserve(Word.Property);
			this.reserve(Word.Protected);
			this.reserve(Word.Public);
			this.reserve(Word.Published);
			this.reserve(Word.Raise);
			this.reserve(Word.Read);
			this.reserve(Word.Readonly);
			this.reserve(Word.Record);
			this.reserve(Word.Register);
			this.reserve(Word.Reintroduce);
			this.reserve(Word.Repeat);
			this.reserve(Word.Requires);
			this.reserve(Word.Resident);
			this.reserve(Word.Resourcestring);
			this.reserve(Word.Safecall);
			this.reserve(Word.Set);
			this.reserve(Word.Shl);
			this.reserve(Word.Shr);
			this.reserve(Word.Stdcall);
			this.reserve(Word.Stored);
			this.reserve(Word.String);
			this.reserve(Word.Then);
			this.reserve(Word.Threadvar);
			this.reserve(Word.To);
			this.reserve(Word.Try);
			this.reserve(Word.Type);
			this.reserve(Word.Unit);
			this.reserve(Word.Until);
			this.reserve(Word.Uses);
			this.reserve(Word.Var);
			this.reserve(Word.Virtual);
			this.reserve(Word.While);
			this.reserve(Word.With);
			this.reserve(Word.Write);
			this.reserve(Word.Writeonly);
		}
		/* 运算关键字 */{
			this.reserve(Operation.And);
			this.reserve(Operation.DivInt);
			this.reserve(Operation.Mod);
			this.reserve(Operation.Not);
			this.reserve(Operation.Or);
			this.reserve(Operation.Xor);
		}
		/* 类型 */ {
			this.reserve(Type.Shortint);
			this.reserve(Type.Integer);
			this.reserve(Type.Longint);
			this.reserve(Type.Byte);
			this.reserve(Type.Word);
			this.reserve(Type.Dword);
			this.reserve(Type.Int64);
			this.reserve(Type.Qword);
			this.reserve(Type.Singer);
			this.reserve(Type.Real);
			this.reserve(Type.Double);
			this.reserve(Type.Extended);
			this.reserve(Type.Comp);
			this.reserve(Type.Char);
			this.reserve(Type.Boolean);
		}
	}
	public char bts(char p){
    	/* 如果peek是大写字母，则化为小写字母 */
        if(p>='A'&&p<='Z'){
        	p =(char) (p+32);
        }
        return p;
    }
	 /* 十进制数转为二进制数，二进数的位数减1 */
    public int c10t2(int x){
    	int count = 0 ;
    	while((x=x/2)>0){
    		count++;
    	}
    	return count;
    }
	public void readch() throws IOException {  
        peek = (char)reader.read();
        peek = bts(peek);
        if((int)peek == 0xffff){  
            this.isEnd = true;  
        }
    }  
      
    public Boolean readch(char ch) throws IOException {  
        readch();  
        if (this.peek != ch) {  
            return false;  
        }
        this.peek = ' ';  
        return true;  
    }  

    public void clear() throws IOException{ /* 清除空白 */
		for( ; ; readch() ) {   
            if(peek == ' ' || peek == '\t' || peek == '\r')  
                continue;  
            else if (peek == '\n')   
                line++;  
            else return ;
        }    	
    }
    
    public Token digitGet() throws IOException{ /* 取数字 */
        int value = 0;int mult = 10;
        //boolean outofnumlimit = false;
        if(peek == '0'){
        	if(readch('x')){
        		readch();
        		mult = 36;
        	}
        	else if(!Character.isDigit(peek)){
        		Num n = new Num(value);
        		tokens.add(n.toString());
        		return n;
        	}
        }
        do {  
            value = mult * value + (Character.isDigit(peek) ? (peek - '0') : (peek - 'a' + 10));
            /*if(value >= numlimit){
            	outofnumlimit = true;
            }*/
            readch();  
        } while (Character.isLetterOrDigit(peek));
        if(c10t2(value)>=24){//outofnumlimit == true
        	Error error = new Error(line,"整数越界");
        	errors.add(error);
        	tokens.add(Word.Inf.toString());
        	return Word.Inf;
        }
        Num n = new Num(value);  
        tokens.add(n.toString());
        return n;  
    }
    
    public Token wordGet() throws IOException{ /* 取词 */
        StringBuffer sb = new StringBuffer();
        do {
            sb.append(peek);  
            readch();  
        } while (Character.isLetterOrDigit(peek));  
        
        String s = sb.toString();
        if(s.length() >= lengthlimit){
        	Error error = new Error(line,"标识符长度超过限制");
        	errors.add(error);
        }
        if(s.length() >= lengthvalid){
        	s = s.substring(0, lengthvalid);
        }
        Word w = words.get(s);  //bsgs
        if(w != null) { /* 词存在 */  
            tokens.add(w.toString());
        } else {
        	w = new Word(s , Tag.ID);
        	tokens.add("id,"+w.toString());
        	//table.put(w, "id");//00000
        	table.add(w.lexme);
        	words.put(s, w);
        }
        return w;  
    }
    
    private boolean isOperation(char peek) {
    	return peek == '+' || peek == '-' || peek == '*' || peek == '/' ||
    			peek == '>' || peek == '<' || peek == '=' || peek == ':';
    }
    /* 取运算符 */
	private Token operationGet() throws IOException{ 
        switch (peek) {
        	case ':':
        		if(readch('=')) return Operation.Asn;
        		else return Operation.Col;
        	case '+':
        		readch();
        		return Operation.Plus;
        	case '-':{
        		if(readch('-')){ /* 清除注释 */
        			while(peek != '\n' && (int)peek != 0xffff)
        				readch();
        			return Word.Note;
        		}
        		else return Operation.Minus;
        	}
        	case '*':
        		readch();
        		return Operation.Mult;
        	case '/':
        		readch();
        		return Operation.DivReal;
        	case '=':
        		readch();
        		return Operation.Eq;
        	case '>': 									
        		if(readch('=')) return Operation.Ge;	
        		else return Operation.Gr;
        	case '<':
        		if(readch('=')) return Operation.Le;
        		else if(peek == '>'){					//peek值已经改变
        			peek = ' ';
        			return Operation.Ne;
        		}
        		else return Operation.Ls;
        }
        return null;
	}
   
	public Token scan() throws IOException {
		/*清除空白*/
		clear();
//		System.out.println(peek);
		
		/*运算符识别*/
		if(isOperation(peek)){
			Token t = operationGet();
			tokens.add(t.toString());
			return t;
		} 
          
        /*整数识别*/
        if(Character.isDigit(peek)) {
        	return digitGet();
        }  
            
        /*关键字&标识符识别*/
        if(Character.isLetter(peek)) {
        	return wordGet();
        }  
          
        /* 单字符词素 */  
        Token tok = new Token(peek);    
        if ((int)peek != 0xffff )   
            tokens.add(tok.toString());  
        peek = ' ';
        return tok;  
    }
}





