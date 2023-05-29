/*     */ package org.springframework.boot.loader;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.jar.Manifest;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.springframework.boot.loader.archive.Archive;
/*     */ import org.springframework.boot.loader.archive.ExplodedArchive;
/*     */ import org.springframework.boot.loader.archive.JarFileArchive;
/*     */ import org.springframework.boot.loader.util.SystemPropertyUtils;
/*     */ import org.springframework.util.Assert;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertiesLauncher
/*     */   extends Launcher
/*     */ {
/*  79 */   private static final Class<?>[] PARENT_ONLY_PARAMS = new Class[] { ClassLoader.class };
/*     */   
/*  81 */   private static final Class<?>[] URLS_AND_PARENT_PARAMS = new Class[] { URL[].class, ClassLoader.class };
/*     */   
/*  83 */   private static final Class<?>[] NO_PARAMS = new Class[0];
/*     */   
/*  85 */   private static final URL[] NO_URLS = new URL[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DEBUG = "loader.debug";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String MAIN = "loader.main";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PATH = "loader.path";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String HOME = "loader.home";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String ARGS = "loader.args";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String CONFIG_NAME = "loader.config.name";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String CONFIG_LOCATION = "loader.config.location";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SET_SYSTEM_PROPERTIES = "loader.system";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private static final Pattern WORD_SEPARATOR = Pattern.compile("\\W+");
/*     */   
/* 138 */   private static final String NESTED_ARCHIVE_SEPARATOR = "!" + File.separator;
/*     */   
/*     */   private final File home;
/*     */   
/* 142 */   private List<String> paths = new ArrayList<>();
/*     */   
/* 144 */   private final Properties properties = new Properties();
/*     */   
/*     */   private Archive parent;
/*     */   
/*     */   public PropertiesLauncher() {
/*     */     try {
/* 150 */       this.home = getHomeDirectory();
/* 151 */       initializeProperties();
/* 152 */       initializePaths();
/* 153 */       this.parent = createArchive();
/*     */     }
/* 155 */     catch (Exception ex) {
/* 156 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected File getHomeDirectory() {
/*     */     try {
/* 162 */       return new File(getPropertyWithDefault("loader.home", "${user.dir}"));
/*     */     }
/* 164 */     catch (Exception ex) {
/* 165 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initializeProperties() throws Exception, IOException {
/* 170 */     List<String> configs = new ArrayList<>();
/* 171 */     if (getProperty("loader.config.location") != null) {
/* 172 */       configs.add(getProperty("loader.config.location"));
/*     */     } else {
/*     */       
/* 175 */       String[] names = getPropertyWithDefault("loader.config.name", "loader").split(",");
/* 176 */       for (String name : names) {
/* 177 */         configs.add("file:" + getHomeDirectory() + "/" + name + ".properties");
/* 178 */         configs.add("classpath:" + name + ".properties");
/* 179 */         configs.add("classpath:BOOT-INF/classes/" + name + ".properties");
/*     */       } 
/*     */     } 
/* 182 */     for (String config : configs) {
/* 183 */       try (InputStream resource = getResource(config)) {
/* 184 */         if (resource != null) {
/* 185 */           debug("Found: " + config);
/* 186 */           loadResource(resource);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 191 */         debug("Not found: " + config);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadResource(InputStream resource) throws IOException, Exception {
/* 198 */     this.properties.load(resource);
/* 199 */     for (Object key : Collections.list(this.properties.propertyNames())) {
/* 200 */       String text = this.properties.getProperty((String)key);
/* 201 */       String value = SystemPropertyUtils.resolvePlaceholders(this.properties, text);
/* 202 */       if (value != null) {
/* 203 */         this.properties.put(key, value);
/*     */       }
/*     */     } 
/* 206 */     if ("true".equals(getProperty("loader.system"))) {
/* 207 */       debug("Adding resolved properties to System properties");
/* 208 */       for (Object key : Collections.list(this.properties.propertyNames())) {
/* 209 */         String value = this.properties.getProperty((String)key);
/* 210 */         System.setProperty((String)key, value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private InputStream getResource(String config) throws Exception {
/* 216 */     if (config.startsWith("classpath:")) {
/* 217 */       return getClasspathResource(config.substring("classpath:".length()));
/*     */     }
/* 219 */     config = handleUrl(config);
/* 220 */     if (isUrl(config)) {
/* 221 */       return getURLResource(config);
/*     */     }
/* 223 */     return getFileResource(config);
/*     */   }
/*     */   
/*     */   private String handleUrl(String path) throws UnsupportedEncodingException {
/* 227 */     if (path.startsWith("jar:file:") || path.startsWith("file:")) {
/* 228 */       path = URLDecoder.decode(path, "UTF-8");
/* 229 */       if (path.startsWith("file:")) {
/* 230 */         path = path.substring("file:".length());
/* 231 */         if (path.startsWith("//")) {
/* 232 */           path = path.substring(2);
/*     */         }
/*     */       } 
/*     */     } 
/* 236 */     return path;
/*     */   }
/*     */   
/*     */   private boolean isUrl(String config) {
/* 240 */     return config.contains("://");
/*     */   }
/*     */   
/*     */   private InputStream getClasspathResource(String config) {
/* 244 */     while (config.startsWith("/")) {
/* 245 */       config = config.substring(1);
/*     */     }
/* 247 */     config = "/" + config;
/* 248 */     debug("Trying classpath: " + config);
/* 249 */     return getClass().getResourceAsStream(config);
/*     */   }
/*     */   
/*     */   private InputStream getFileResource(String config) throws Exception {
/* 253 */     File file = new File(config);
/* 254 */     debug("Trying file: " + config);
/* 255 */     if (file.canRead()) {
/* 256 */       return new FileInputStream(file);
/*     */     }
/* 258 */     return null;
/*     */   }
/*     */   
/*     */   private InputStream getURLResource(String config) throws Exception {
/* 262 */     URL url = new URL(config);
/* 263 */     if (exists(url)) {
/* 264 */       URLConnection con = url.openConnection();
/*     */       try {
/* 266 */         return con.getInputStream();
/*     */       }
/* 268 */       catch (IOException ex) {
/*     */         
/* 270 */         if (con instanceof HttpURLConnection) {
/* 271 */           ((HttpURLConnection)con).disconnect();
/*     */         }
/* 273 */         throw ex;
/*     */       } 
/*     */     } 
/* 276 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean exists(URL url) throws IOException {
/* 281 */     URLConnection connection = url.openConnection();
/*     */     try {
/* 283 */       connection.setUseCaches(connection.getClass().getSimpleName().startsWith("JNLP"));
/* 284 */       if (connection instanceof HttpURLConnection) {
/* 285 */         HttpURLConnection httpConnection = (HttpURLConnection)connection;
/* 286 */         httpConnection.setRequestMethod("HEAD");
/* 287 */         int responseCode = httpConnection.getResponseCode();
/* 288 */         if (responseCode == 200) {
/* 289 */           return true;
/*     */         }
/* 291 */         if (responseCode == 404) {
/* 292 */           return false;
/*     */         }
/*     */       } 
/* 295 */       return (connection.getContentLength() >= 0);
/*     */     } finally {
/*     */       
/* 298 */       if (connection instanceof HttpURLConnection) {
/* 299 */         ((HttpURLConnection)connection).disconnect();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initializePaths() throws Exception {
/* 305 */     String path = getProperty("loader.path");
/* 306 */     if (path != null) {
/* 307 */       this.paths = parsePathsProperty(path);
/*     */     }
/* 309 */     debug("Nested archive paths: " + this.paths);
/*     */   }
/*     */   
/*     */   private List<String> parsePathsProperty(String commaSeparatedPaths) {
/* 313 */     List<String> paths = new ArrayList<>();
/* 314 */     for (String path : commaSeparatedPaths.split(",")) {
/* 315 */       path = cleanupPath(path);
/*     */       
/* 317 */       path = "".equals(path) ? "/" : path;
/* 318 */       paths.add(path);
/*     */     } 
/* 320 */     if (paths.isEmpty()) {
/* 321 */       paths.add("lib");
/*     */     }
/* 323 */     return paths;
/*     */   }
/*     */   
/*     */   protected String[] getArgs(String... args) throws Exception {
/* 327 */     String loaderArgs = getProperty("loader.args");
/* 328 */     if (loaderArgs != null) {
/* 329 */       String[] defaultArgs = loaderArgs.split("\\s+");
/* 330 */       String[] additionalArgs = args;
/* 331 */       args = new String[defaultArgs.length + additionalArgs.length];
/* 332 */       System.arraycopy(defaultArgs, 0, args, 0, defaultArgs.length);
/* 333 */       System.arraycopy(additionalArgs, 0, args, defaultArgs.length, additionalArgs.length);
/*     */     } 
/* 335 */     return args;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMainClass() throws Exception {
/* 340 */     String mainClass = getProperty("loader.main", "Start-Class");
/* 341 */     if (mainClass == null) {
/* 342 */       throw new IllegalStateException("No 'loader.main' or 'Start-Class' specified");
/*     */     }
/* 344 */     return mainClass;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ClassLoader createClassLoader(List<Archive> archives) throws Exception {
/* 349 */     Set<URL> urls = new LinkedHashSet<>(archives.size());
/* 350 */     for (Archive archive : archives) {
/* 351 */       urls.add(archive.getUrl());
/*     */     }
/* 353 */     ClassLoader loader = new LaunchedURLClassLoader(urls.<URL>toArray(NO_URLS), getClass().getClassLoader());
/* 354 */     debug("Classpath: " + urls);
/* 355 */     String customLoaderClassName = getProperty("loader.classLoader");
/* 356 */     if (customLoaderClassName != null) {
/* 357 */       loader = wrapWithCustomClassLoader(loader, customLoaderClassName);
/* 358 */       debug("Using custom class loader: " + customLoaderClassName);
/*     */     } 
/* 360 */     return loader;
/*     */   }
/*     */ 
/*     */   
/*     */   private ClassLoader wrapWithCustomClassLoader(ClassLoader parent, String className) throws Exception {
/* 365 */     Class<ClassLoader> type = (Class)Class.forName(className, true, parent);
/* 366 */     ClassLoader classLoader = newClassLoader(type, PARENT_ONLY_PARAMS, new Object[] { parent });
/* 367 */     if (classLoader == null) {
/* 368 */       classLoader = newClassLoader(type, URLS_AND_PARENT_PARAMS, new Object[] { NO_URLS, parent });
/*     */     }
/* 370 */     if (classLoader == null) {
/* 371 */       classLoader = newClassLoader(type, NO_PARAMS, new Object[0]);
/*     */     }
/* 373 */     Assert.notNull(classLoader, "Unable to create class loader for " + className);
/* 374 */     return classLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   private ClassLoader newClassLoader(Class<ClassLoader> loaderClass, Class<?>[] parameterTypes, Object... initargs) throws Exception {
/*     */     try {
/* 380 */       Constructor<ClassLoader> constructor = loaderClass.getDeclaredConstructor(parameterTypes);
/* 381 */       constructor.setAccessible(true);
/* 382 */       return constructor.newInstance(initargs);
/*     */     }
/* 384 */     catch (NoSuchMethodException ex) {
/* 385 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getProperty(String propertyKey) throws Exception {
/* 390 */     return getProperty(propertyKey, (String)null, (String)null);
/*     */   }
/*     */   
/*     */   private String getProperty(String propertyKey, String manifestKey) throws Exception {
/* 394 */     return getProperty(propertyKey, manifestKey, (String)null);
/*     */   }
/*     */   
/*     */   private String getPropertyWithDefault(String propertyKey, String defaultValue) throws Exception {
/* 398 */     return getProperty(propertyKey, (String)null, defaultValue);
/*     */   }
/*     */   
/*     */   private String getProperty(String propertyKey, String manifestKey, String defaultValue) throws Exception {
/* 402 */     if (manifestKey == null) {
/* 403 */       manifestKey = propertyKey.replace('.', '-');
/* 404 */       manifestKey = toCamelCase(manifestKey);
/*     */     } 
/* 406 */     String property = SystemPropertyUtils.getProperty(propertyKey);
/* 407 */     if (property != null) {
/* 408 */       String value = SystemPropertyUtils.resolvePlaceholders(this.properties, property);
/* 409 */       debug("Property '" + propertyKey + "' from environment: " + value);
/* 410 */       return value;
/*     */     } 
/* 412 */     if (this.properties.containsKey(propertyKey)) {
/* 413 */       String value = SystemPropertyUtils.resolvePlaceholders(this.properties, this.properties
/* 414 */           .getProperty(propertyKey));
/* 415 */       debug("Property '" + propertyKey + "' from properties: " + value);
/* 416 */       return value;
/*     */     } 
/*     */     try {
/* 419 */       if (this.home != null)
/*     */       {
/* 421 */         try (ExplodedArchive archive = new ExplodedArchive(this.home, false)) {
/* 422 */           Manifest manifest1 = archive.getManifest();
/* 423 */           if (manifest1 != null) {
/* 424 */             String value = manifest1.getMainAttributes().getValue(manifestKey);
/* 425 */             if (value != null) {
/* 426 */               debug("Property '" + manifestKey + "' from home directory manifest: " + value);
/* 427 */               return SystemPropertyUtils.resolvePlaceholders(this.properties, value);
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/*     */       }
/* 433 */     } catch (IllegalStateException illegalStateException) {}
/*     */ 
/*     */ 
/*     */     
/* 437 */     Manifest manifest = createArchive().getManifest();
/* 438 */     if (manifest != null) {
/* 439 */       String value = manifest.getMainAttributes().getValue(manifestKey);
/* 440 */       if (value != null) {
/* 441 */         debug("Property '" + manifestKey + "' from archive manifest: " + value);
/* 442 */         return SystemPropertyUtils.resolvePlaceholders(this.properties, value);
/*     */       } 
/*     */     } 
/* 445 */     return (defaultValue != null) ? SystemPropertyUtils.resolvePlaceholders(this.properties, defaultValue) : defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<Archive> getClassPathArchives() throws Exception {
/* 451 */     List<Archive> lib = new ArrayList<>();
/* 452 */     for (String path : this.paths) {
/* 453 */       for (Archive archive : getClassPathArchives(path)) {
/* 454 */         if (archive instanceof ExplodedArchive) {
/* 455 */           List<Archive> nested = new ArrayList<>(archive.getNestedArchives(new ArchiveEntryFilter()));
/* 456 */           nested.add(0, archive);
/* 457 */           lib.addAll(nested);
/*     */           continue;
/*     */         } 
/* 460 */         lib.add(archive);
/*     */       } 
/*     */     } 
/*     */     
/* 464 */     addNestedEntries(lib);
/* 465 */     return lib;
/*     */   }
/*     */   
/*     */   private List<Archive> getClassPathArchives(String path) throws Exception {
/* 469 */     String root = cleanupPath(handleUrl(path));
/* 470 */     List<Archive> lib = new ArrayList<>();
/* 471 */     File file = new File(root);
/* 472 */     if (!"/".equals(root)) {
/* 473 */       if (!isAbsolutePath(root)) {
/* 474 */         file = new File(this.home, root);
/*     */       }
/* 476 */       if (file.isDirectory()) {
/* 477 */         debug("Adding classpath entries from " + file);
/* 478 */         ExplodedArchive explodedArchive = new ExplodedArchive(file, false);
/* 479 */         lib.add(explodedArchive);
/*     */       } 
/*     */     } 
/* 482 */     Archive archive = getArchive(file);
/* 483 */     if (archive != null) {
/* 484 */       debug("Adding classpath entries from archive " + archive.getUrl() + root);
/* 485 */       lib.add(archive);
/*     */     } 
/* 487 */     List<Archive> nestedArchives = getNestedArchives(root);
/* 488 */     if (nestedArchives != null) {
/* 489 */       debug("Adding classpath entries from nested " + root);
/* 490 */       lib.addAll(nestedArchives);
/*     */     } 
/* 492 */     return lib;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAbsolutePath(String root) {
/* 497 */     return (root.contains(":") || root.startsWith("/"));
/*     */   }
/*     */   
/*     */   private Archive getArchive(File file) throws IOException {
/* 501 */     if (isNestedArchivePath(file)) {
/* 502 */       return null;
/*     */     }
/* 504 */     String name = file.getName().toLowerCase(Locale.ENGLISH);
/* 505 */     if (name.endsWith(".jar") || name.endsWith(".zip")) {
/* 506 */       return (Archive)new JarFileArchive(file);
/*     */     }
/* 508 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isNestedArchivePath(File file) {
/* 512 */     return file.getPath().contains(NESTED_ARCHIVE_SEPARATOR);
/*     */   }
/*     */   private List<Archive> getNestedArchives(String path) throws Exception {
/*     */     JarFileArchive jarFileArchive;
/* 516 */     Archive parent = this.parent;
/* 517 */     String root = path;
/* 518 */     if ((!root.equals("/") && root.startsWith("/")) || parent.getUrl().equals(this.home.toURI().toURL()))
/*     */     {
/* 520 */       return null;
/*     */     }
/* 522 */     int index = root.indexOf('!');
/* 523 */     if (index != -1) {
/* 524 */       File file = new File(this.home, root.substring(0, index));
/* 525 */       if (root.startsWith("jar:file:")) {
/* 526 */         file = new File(root.substring("jar:file:".length(), index));
/*     */       }
/* 528 */       jarFileArchive = new JarFileArchive(file);
/* 529 */       root = root.substring(index + 1);
/* 530 */       while (root.startsWith("/")) {
/* 531 */         root = root.substring(1);
/*     */       }
/*     */     } 
/* 534 */     if (root.endsWith(".jar")) {
/* 535 */       File file = new File(this.home, root);
/* 536 */       if (file.exists()) {
/* 537 */         jarFileArchive = new JarFileArchive(file);
/* 538 */         root = "";
/*     */       } 
/*     */     } 
/* 541 */     if (root.equals("/") || root.equals("./") || root.equals("."))
/*     */     {
/* 543 */       root = "";
/*     */     }
/* 545 */     Archive.EntryFilter filter = new PrefixMatchingArchiveFilter(root);
/* 546 */     List<Archive> archives = new ArrayList<>(jarFileArchive.getNestedArchives(filter));
/* 547 */     if (("".equals(root) || ".".equals(root)) && !path.endsWith(".jar") && jarFileArchive != this.parent)
/*     */     {
/*     */       
/* 550 */       archives.add(jarFileArchive);
/*     */     }
/* 552 */     return archives;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addNestedEntries(List<Archive> lib) {
/*     */     try {
/* 560 */       lib.addAll(this.parent.getNestedArchives(entry -> entry.isDirectory() ? entry.getName().equals("BOOT-INF/classes/") : entry.getName().startsWith("BOOT-INF/lib/")));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 567 */     catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String cleanupPath(String path) {
/* 573 */     path = path.trim();
/*     */     
/* 575 */     if (path.startsWith("./")) {
/* 576 */       path = path.substring(2);
/*     */     }
/* 578 */     String lowerCasePath = path.toLowerCase(Locale.ENGLISH);
/* 579 */     if (lowerCasePath.endsWith(".jar") || lowerCasePath.endsWith(".zip")) {
/* 580 */       return path;
/*     */     }
/* 582 */     if (path.endsWith("/*")) {
/* 583 */       path = path.substring(0, path.length() - 1);
/*     */ 
/*     */     
/*     */     }
/* 587 */     else if (!path.endsWith("/") && !path.equals(".")) {
/* 588 */       path = path + "/";
/*     */     } 
/*     */     
/* 591 */     return path;
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 595 */     PropertiesLauncher launcher = new PropertiesLauncher();
/* 596 */     args = launcher.getArgs(args);
/* 597 */     launcher.launch(args);
/*     */   }
/*     */   
/*     */   public static String toCamelCase(CharSequence string) {
/* 601 */     if (string == null) {
/* 602 */       return null;
/*     */     }
/* 604 */     StringBuilder builder = new StringBuilder();
/* 605 */     Matcher matcher = WORD_SEPARATOR.matcher(string);
/* 606 */     int pos = 0;
/* 607 */     while (matcher.find()) {
/* 608 */       builder.append(capitalize(string.subSequence(pos, matcher.end()).toString()));
/* 609 */       pos = matcher.end();
/*     */     } 
/* 611 */     builder.append(capitalize(string.subSequence(pos, string.length()).toString()));
/* 612 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private static String capitalize(String str) {
/* 616 */     return Character.toUpperCase(str.charAt(0)) + str.substring(1);
/*     */   }
/*     */   
/*     */   private void debug(String message) {
/* 620 */     if (Boolean.getBoolean("loader.debug")) {
/* 621 */       System.out.println(message);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class PrefixMatchingArchiveFilter
/*     */     implements Archive.EntryFilter
/*     */   {
/*     */     private final String prefix;
/*     */ 
/*     */     
/* 633 */     private final PropertiesLauncher.ArchiveEntryFilter filter = new PropertiesLauncher.ArchiveEntryFilter();
/*     */     
/*     */     private PrefixMatchingArchiveFilter(String prefix) {
/* 636 */       this.prefix = prefix;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(Archive.Entry entry) {
/* 641 */       if (entry.isDirectory()) {
/* 642 */         return entry.getName().equals(this.prefix);
/*     */       }
/* 644 */       return (entry.getName().startsWith(this.prefix) && this.filter.matches(entry));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ArchiveEntryFilter
/*     */     implements Archive.EntryFilter
/*     */   {
/*     */     private static final String DOT_JAR = ".jar";
/*     */     
/*     */     private static final String DOT_ZIP = ".zip";
/*     */ 
/*     */     
/*     */     private ArchiveEntryFilter() {}
/*     */ 
/*     */     
/*     */     public boolean matches(Archive.Entry entry) {
/* 661 */       return (entry.getName().endsWith(".jar") || entry.getName().endsWith(".zip"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\olatu\OneDrive\Documents\Calypso\syncbase\sync_base.jar!\org\springframework\boot\loader\PropertiesLauncher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */