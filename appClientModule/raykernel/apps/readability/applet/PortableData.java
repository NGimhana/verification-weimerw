package raykernel.apps.readability.applet;

import java.util.LinkedList;
import java.util.List;

import raykernel.apps.readability.snippet.Snippet;

public class PortableData
{
	public static final String[] snippets = {

			"\n" + "			Object ret = body.eval(callstack, interpreter);\n" + "\n" + "			boolean breakout = false;\n"
					+ "			if(ret instanceof ReturnControl)\n" + "			{\n" + "				switch(((ReturnControl)ret).kind )\n"
					+ "				{\n" + "					case RETURN:\n" + "						return ret;\n",

			"\n" + "        if (actionList.size() == 1) {\n" + "            ActionMenu menu = actionList.get(0);\n"
					+ "\n" + "            if (menu.getSubItems().length == 0) {\n" + "                return null;\n"
					+ "            }\n" + "\n" + "            if (menu.getSubItems().length == 1) {\n"
					+ "                Action action = menu.getSubItems()[0].getAction();\n",

			"		private String compactString(String source) {\n"
					+ "			String result= DELTA_START + source.substring(fPrefix, source.length() - fSuffix + 1) + DELTA_END;\n"
					+ "			if (fPrefix > 0)\n" + "				result= computeCommonPrefix() + result;\n"
					+ "			if (fSuffix > 0)\n" + "				result= result + computeCommonSuffix();\n",

			"    /**\n" + "     * Constructor, with a argument reference to the PUBLIC User Object which\n"
					+ "     * is null if this is the SYS or PUBLIC user.\n" + "     *\n"
					+ "     * The dependency upon a GranteeManager is undesirable.  Hopefully we\n"
					+ "     * can get rid of this dependency with an IOC or Listener re-design.\n" + "     */\n"
					+ "    Grantee(String name, Grantee inGrantee,\n"
					+ "            GranteeManager man) throws HsqlException {\n" + "\n"
					+ "        rightsMap      = new IntValueHashMap();\n" + "        granteeName    = name;\n"
					+ "        granteeManager = man;\n",

			"    /**\n" + "     * Quits the application without any questions.\n" + "     */\n"
					+ "    public void quit() {\n" + "        getConnectController().quitGame(true);\n"
					+ "        if (!windowed) {\n" + "            gd.setFullScreenWindow(null);\n" + "        }\n"
					+ "        System.exit(0);\n",

			"    xsp = jj_scanpos;\n" + "    if (jj_scan_token(100)) {\n" + "    jj_scanpos = xsp;\n"
					+ "    if (jj_scan_token(101)) return true;\n",

			"  /**\n" + "   * Attention: DO NOT USE THIS!\n"
					+ "   * Under Os/2 it has some problems with calculating the real Date!\n" + "   * \n"
					+ "   * @deprecated\n" + "   */\n" + "  public Date(int daysSince1970) {\n" + "\n"
					+ "    long l = (long) daysSince1970 * 24 * 60 * 60 * 1000;\n"
					+ "    java.util.Date d = new java.util.Date(l);\n"
					+ "    Calendar cal = Calendar.getInstance();\n",

			"	public TestMethodRunner(Object test, Method method, RunNotifier notifier, Description description) {\n"
					+ "		super(test.getClass(), Before.class, After.class, test);\n" + "		fTest= test;\n"
					+ "		fMethod= method;\n",

			"    /**\n" + "     * Returns a vector containing the URI (type + path) for all the databases.\n"
					+ "     */\n" + "    public static Vector getDatabaseURIs() {\n" + "\n"
					+ "        Vector   v  = new Vector();\n"
					+ "        Iterator it = databaseIDMap.values().iterator();\n" + "\n"
					+ "        while (it.hasNext()) {\n" + "            Database db = (Database) it.next();\n",

			"    private void moveUnit(KeyEvent e) {\n" + "        if (!parent.isMapboardActionsEnabled()) {\n"
					+ "            return;\n" + "        }\n" + "        \n" + "        switch (e.getKeyCode()) {\n"
					+ "        case KeyEvent.VK_ESCAPE:\n" + "            // main menu\n" + "            break;\n"
					+ "        case KeyEvent.VK_NUMPAD1:\n" + "        case KeyEvent.VK_END:\n"
					+ "            inGameController.moveActiveUnit(Map.SW);\n",

			"\n" + "		if ( clas == null )\n" + "			throw new ClassNotFoundException(\n"
					+ "				\"Class: \" + value+ \" not found in namespace\");\n" + "\n" + "		asClass = clas;\n"
					+ "		return asClass;\n",

			"\n" + "        \n" + "        btPanel.add(cancel);\n" + "\n"
					+ "        getRootPane().setDefaultButton(ok);\n" + "        \n"
					+ "        panel.add(btPanel, BorderLayout.SOUTH);\n",

			"	 * @param expected expected value\n" + "	 * @param actual actual value\n" + "	 */\n"
					+ "	static public void assertEquals(String message, Object expected, Object actual) {\n"
					+ "		if (expected == null && actual == null)\n" + "			return;\n"
					+ "		if (expected != null && isEquals(expected, actual))\n" + "			return;\n"
					+ "		else if (expected instanceof String && actual instanceof String) {\n"
					+ "			String cleanMessage= message == null ? \"\" : message;\n",

			"    Object removeName(String name) throws HsqlException {\n" + "\n"
					+ "        Object owner = nameList.remove(name);\n" + "\n" + "        if (owner == null) {\n"
					+ "\n" + "            // should contain name\n"
					+ "            throw Trace.error(Trace.GENERAL_ERROR);\n" + "        }\n" + "\n"
					+ "        return owner;\n",

			"\n"
					+ "        int stepSize = Math.min((option.getMaximumValue() - option.getMinimumValue()) / 10, 1000);\n"
					+ "        spinner = new JSpinner(new SpinnerNumberModel(option.getValue(), option.getMinimumValue(),\n"
					+ "                option.getMaximumValue(), Math.max(1, stepSize)));\n"
					+ "        spinner.setToolTipText(option.getShortDescription());\n",

			"		if ( parent != null )\n" + "			setStrictJava( parent.getStrictJava() );\n"
					+ "		this.sourceFileInfo = sourceFileInfo;\n" + "\n"
					+ "		BshClassManager bcm = BshClassManager.createClassManager( this );\n",

			"        \n" + "        mDevices = new Vector<DeviceIf>();\n" + "        \n"
					+ "        DeviceFileHandling reader = new DeviceFileHandling();\n" + "\n"
					+ "        for (int i = 0; i < num; i++) {\n"
					+ "            String classname = (String) in.readObject();\n",

			"	/**\n" + "	 * Do not use. Testing purposes only.\n" + "	 */\n"
					+ "	public Result runMain(String... args) {\n"
					+ "		System.out.println(\"JUnit version \" + Version.id());\n"
					+ "		List<Class<?>> classes= new ArrayList<Class<?>>();\n"
					+ "		List<Failure> missingClasses= new ArrayList<Failure>();\n",

			"    /**\n" + "     * temp constraint constructor\n" + "     */\n"
					+ "    Constraint(HsqlName name, int[] mainCols, Table refTable, int[] refCols,\n"
					+ "               int type, int deleteAction, int updateAction) {\n" + "\n"
					+ "        core              = new ConstraintCore();\n" + "        constName         = name;\n"
					+ "        constType         = type;\n",

			"        int eventId = active? ON : OFF;\n"
					+ "        ActionEvent blinkEvent = new ActionEvent(this,eventId,\"blink\");\n" + "        \n"
					+ "        fireActionEvent(blinkEvent);\n",

			"		else if ( isPrimitive( returnType ) ) \n" + "		{\n" + "			int opcode = IRETURN;\n" + "			String type;\n"
					+ "			String meth;\n",

			"    /**\n"
					+ "     * Returns the PluginPanel\n"
					+ "     * @return Panel\n"
					+ "     */\n"
					+ "    public JPanel createSettingsPanel() {\n"
					+ "      mPanel = new CapturePluginPanel(mOwner, mCloneData);\n"
					+ "      mPanel.setBorder(Borders.createEmptyBorder(Sizes.DLUY5,Sizes.DLUX5,Sizes.DLUY5,Sizes.DLUX5));\n"
					+ "      mPanel.setSelectedTab(mCurrentPanel);\n",

			"	protected void printFailures(Result result) {\n" + "		if (result.getFailureCount() == 0)\n"
					+ "			return;\n" + "		if (result.getFailureCount() == 1)\n"
					+ "			getWriter().println(\"There was \" + result.getFailureCount() + \" failure:\");\n"
					+ "		else\n"
					+ "			getWriter().println(\"There were \" + result.getFailureCount() + \" failures:\");\n",

			"    public static long getNormalisedTime(long t) {\n" + "\n" + "        synchronized (tempCalDefault) {\n"
					+ "            setTimeInMillis(tempCalDefault, t);\n"
					+ "            resetToTime(tempCalDefault);\n" + "\n"
					+ "            return getTimeInMillis(tempCalDefault);\n",

			"\n"
					+ "            public boolean check(Unit u, PathNode p) {\n"
					+ "                if (p.getTile().getSettlement() != null && p.getTile().getSettlement().getOwner() == player\n"
					+ "                        && p.getTile().getSettlement() != inSettlement) {\n"
					+ "                    Settlement s = p.getTile().getSettlement();\n"
					+ "                    int turns = p.getTurns();\n"
					+ "                    destinations.add(new ChoiceItem(s.toString() + \" (\" + turns + \")\", s));\n",

			"\n" + "     if ((bufpos + 1) >= len)\n"
					+ "        System.arraycopy(buffer, bufpos - len + 1, ret, 0, len);\n" + "     else\n" + "     {\n"
					+ "        System.arraycopy(buffer, bufsize - (len - bufpos - 1), ret, 0,\n"
					+ "                                                          len - bufpos - 1);\n"
					+ "        System.arraycopy(buffer, 0, ret, len - bufpos - 1, bufpos + 1);\n",

			"    /**\n" + "     * Compute the proper position for a centered window\n" + "     */\n"
					+ "    private Point comuteDisplayPointCentre(Dimension dim) {\n"
					+ "        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();\n"
					+ "        int x = (screen.width - dim.width) / 2;\n"
					+ "        int y = (screen.height - dim.height) / 2;\n",

			"	public void filter(Filter filter) throws NoTestsRemainException {\n"
					+ "		for (Iterator<Runner> iter= fRunners.iterator(); iter.hasNext();) {\n"
					+ "			Runner runner= iter.next();\n" + "			if (filter.shouldRun(runner.getDescription()))\n"
					+ "				filter.apply(runner);\n" + "			else\n" + "				iter.remove();\n",

			"        boolean    hasReturnValue;\n" + "\n" + "        outlen = parameters.length;\n"
					+ "        offset = 0;\n",

			"    /**\n"
					+ "     * Sets the currently chosen <code>MapTransform</code>.\n"
					+ "     * @param mt The transform that should be applied to a\n"
					+ "     *      <code>Tile</code> that is clicked on the map.\n"
					+ "     */\n"
					+ "    public void setMapTransform(MapTransform mt) {\n"
					+ "        currentMapTransform = mt;\n"
					+ "        MapControlsAction mca = (MapControlsAction) freeColClient.getActionManager().getFreeColAction(MapControlsAction.ID);\n"
					+ "        if (mca.getMapControls() != null) {\n"
					+ "            mca.getMapControls().update(mt);\n",

			"			\n" + "			if (iterateOverMe instanceof String)\n"
					+ "				return createEnumeration(((String)iterateOverMe).toCharArray());\n" + "			\n"
					+ "			if (iterateOverMe instanceof StringBuffer)\n" + "				return createEnumeration(\n"
					+ "					iterateOverMe.toString().toCharArray());\n" + "\n"
					+ "			throw new IllegalArgumentException(\n"
					+ "				\"Cannot enumerate object of type \"+iterateOverMe.getClass());\n",

			"  /**\n" + "   * Constructs a new Date object, initialized with the current date.\n" + "   */\n"
					+ "  public Date() {\n" + "    Calendar mCalendar = Calendar.getInstance();\n"
					+ "    mYear = mCalendar.get(Calendar.YEAR);\n"
					+ "    mMonth = mCalendar.get(Calendar.MONTH) + 1;\n",

			"		/**\n"
					+ "		 * @param contextLength the maximum length for <code>expected</code> and <code>actual</code>. When contextLength \n"
					+ "		 * is exceeded, the Strings are shortened\n"
					+ "		 * @param expected the expected string value\n"
					+ "		 * @param actual the actual string value\n" + "		 */\n"
					+ "		public ComparisonCompactor(int contextLength, String expected, String actual) {\n"
					+ "			fContextLength= contextLength;\n" + "			fExpected= expected;\n" + "			fActual= actual;\n",

			"\n" + "            case CompiledStatement.DELETE :\n"
					+ "                return executeDeleteStatement(cs);\n" + "\n"
					+ "            case CompiledStatement.CALL :\n"
					+ "                return executeCallStatement(cs);\n" + "\n"
					+ "            case CompiledStatement.DDL :\n"
					+ "                return executeDDLStatement(cs);\n",

			"    /**\n"
					+ "     * Creates a new <code>DisbandUnitAction</code>.\n"
					+ "     * \n"
					+ "     * @param freeColClient The main controller object for the client.\n"
					+ "     */\n"
					+ "    DisbandUnitAction(FreeColClient freeColClient) {\n"
					+ "        super(freeColClient, \"unit.state.8\", null, KeyStroke.getKeyStroke('D', 0));\n"
					+ "        putValue(BUTTON_IMAGE, freeColClient.getImageLibrary().getUnitButtonImageIcon(ImageLibrary.UNIT_BUTTON_DISBAND,\n"
					+ "                0));\n"
					+ "        putValue(BUTTON_ROLLOVER_IMAGE, freeColClient.getImageLibrary().getUnitButtonImageIcon(\n"
					+ "                ImageLibrary.UNIT_BUTTON_DISBAND, 1));\n",

			"			Class clas = object.getClass();\n" + "			Field field = Reflect.resolveJavaField( \n"
					+ "				clas, name, false/*onlyStatic*/ );\n" + "			if ( field != null )\n"
					+ "				return new Variable( \n" + "					name, field.getType(), new LHS( object, field ) );\n",

			"    \n"
					+ "    for(AbstractPluginProgramFormating config : mConfigs)\n"
					+ "      if(config != null && config.isValid())\n"
					+ "        list.add(new ProgramReceiveTarget(this, config.getName(), config.getId()));\n"
					+ "    \n"
					+ "    if(list.isEmpty())\n"
					+ "      list.add(new ProgramReceiveTarget(this, DEFAULT_CONFIG.getName(), DEFAULT_CONFIG.getId()));\n"
					+ "    \n" + "    return list.toArray(new ProgramReceiveTarget[list.size()]);\n",

			"	Class<? extends Throwable> expectedException(Method method) {\n"
					+ "		Test annotation= method.getAnnotation(Test.class);\n"
					+ "		if (annotation.expected() == None.class)\n" + "			return null;\n" + "		else\n"
					+ "			return annotation.expected();\n",

			"            row[1] = ns.getCatalogName(row[0]);\n"
					+ "            row[2] = schema.equals(defschema) ? Boolean.TRUE\n"
					+ "                                              : Boolean.FALSE;\n" + "\n"
					+ "            t.insertSys(row);\n",

			"    /**\n" + "     * Handles an \"deliverGift\"-request.\n" + "     * \n"
					+ "     * @param element The element (root element in a DOM-parsed XML tree) that\n"
					+ "     *            holds all the information.\n" + "     */\n"
					+ "    private Element deliverGift(Element element) {\n"
					+ "        Element unitElement = Message.getChildElement(element, Unit.getXMLElementTagName());\n"
					+ "\n"
					+ "        Unit unit = (Unit) getGame().getFreeColGameObject(unitElement.getAttribute(\"ID\"));\n"
					+ "        unit.readFromXMLElement(unitElement);\n",

			"      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {\n" + "      case EQ:\n"
					+ "        t = jj_consume_token(EQ);\n" + "        break;\n" + "      case NE:\n"
					+ "        t = jj_consume_token(NE);\n",

			"  public synchronized void removeProgram(Program program) {\n"
					+ "    PluginTreeNode node = findProgramTreeNode(program, false);\n" + "    if (node != null) {\n"
					+ "      mChildNodes.remove(node);\n" + "      if (mMarker != null) {\n"
					+ "        program.unmark(mMarker);\n",

			"		private TestClassRunnerForParameters(Class<?> klass, Object[] parameters, int i) {\n"
					+ "			super(klass);\n" + "			fParameters= parameters;\n" + "			fParameterSetNumber= i;\n",

			"        void link(IndexRowIterator other) {\n" + "\n" + "            other.next = next;\n"
					+ "            other.last = this;\n" + "            next.last  = other;\n",

			"        final String heightText = Messages.message(\"height\");\n"
					+ "        \n"
					+ "        final JTextField inputWidth = new JTextField(Integer.toString(DEFAULT_WIDTH), COLUMNS);\n"
					+ "        final JTextField inputHeight = new JTextField(Integer.toString(DEFAULT_HEIGHT), COLUMNS);\n",

			"	/**\n" + "		Get the top level namespace or this namespace if we are the top.\n"
					+ "		Note: this method should probably return type bsh.This to be consistent\n"
					+ "		with getThis();\n" + "	*/\n"
					+ "    public This getGlobal( Interpreter declaringInterpreter )\n" + "    {\n"
					+ "		if ( parent != null )\n" + "			return parent.getGlobal( declaringInterpreter );\n"
					+ "		else\n" + "			return getThis( declaringInterpreter );\n",

			"    /**\n"
					+ "     * Read Settings\n"
					+ "     * @param stream\n"
					+ "     * @throws IOException\n"
					+ "     * @throws ClassNotFoundException\n"
					+ "     */\n"
					+ "    public void readData(ObjectInputStream stream) throws IOException, ClassNotFoundException {\n"
					+ "      int version = stream.readInt();\n" + "      mNumber = stream.readInt();\n"
					+ "      mName = stream.readUTF();\n",

			"	/**\n" + "	 * Constructs a comparison failure.\n"
					+ "	 * @param message the identifying message or null\n"
					+ "	 * @param expected the expected string value\n" + "	 * @param actual the actual string value\n"
					+ "	 */\n" + "	public ComparisonFailure (String message, String expected, String actual) {\n"
					+ "		super (message);\n" + "		fExpected= expected;\n" + "		fActual= actual;\n",

			"    public void close() {\n" + "\n" + "        if (isClosed) {\n" + "            return;\n"
					+ "        }\n" + "\n" + "        isClosed = true;\n" + "\n" + "        try {\n"
					+ "            resultOut.setResultType(ResultConstants.SQLDISCONNECT);\n",

			"        public void actionPerformed(ActionEvent evt) {\n" + "            if (!hasFocus()) {\n"
					+ "                stopBlinking();\n" + "            }\n" + "\n" + "            if (blinkOn) {\n"
					+ "                setOpaque(false);\n" + "                blinkOn = false;\n",

			"	private static String getBaseName( String className ) \n" + "	{\n"
					+ "		int i = className.indexOf(\"$\");\n" + "		if ( i == -1 )\n" + "			return className;\n" + "\n"
					+ "		return className.substring(i+1);\n",

			"\n"
					+ "    panel.add(UiUtilities.createHelpTextArea(mLocalizer.msg(\"help\",\"No endtime defined\")), cc.xy(1,1));\n"
					+ "    \n" + "    mTimePanel = new TimeDateChooserPanel(date);\n"
					+ "    panel.add(mTimePanel, cc.xy(1,3));\n",

			"		try {\n" + "			suiteMethod= klass.getMethod(\"suite\");\n"
					+ "			if (! Modifier.isStatic(suiteMethod.getModifiers())) {\n"
					+ "				throw new Exception(klass.getName() + \".suite() must be static\");\n" + "			}\n"
					+ "			suite= (Test) suiteMethod.invoke(null); // static method\n",

			"\n" + "            // ----------------------------------------------------------------\n"
					+ "            // required\n"
					+ "            // ----------------------------------------------------------------\n"
					+ "            addColumn(t, \"PROCEDURE_CAT\", Types.VARCHAR);\n"
					+ "            addColumn(t, \"PROCEDURE_SCHEM\", Types.VARCHAR);\n"
					+ "            addColumn(t, \"PROCEDURE_NAME\", Types.VARCHAR, false);    // not null\n",

			"\n"
					+ "        if (missionChip == null) {\n"
					+ "            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()\n"
					+ "                    .getDefaultConfiguration();\n"
					+ "            loadMissionChip(gc, color, expertMission);\n" + "\n"
					+ "            if (expertMission) {\n"
					+ "                missionChip = expertMissionChips.get(color);\n",

			"	/**\n" + "		Swap in the value as the new top of the stack and return the old\n" + "		value.\n" + "	*/\n"
					+ "	public NameSpace swap( NameSpace newTop ) {\n"
					+ "		NameSpace oldTop = (NameSpace)(stack.elementAt(0));\n"
					+ "		stack.setElementAt( newTop, 0 );\n" + "		return oldTop;\n",

			"      \n" + "      String classname = (String) in.readObject();\n"
					+ "      String devname = (String)in.readObject();\n" + "      \n"
					+ "      DeviceIf dev = DriverFactory.getInstance().createDevice(classname, devname);\n",

			"			String simpleName= runnerClass.getSimpleName();\n"
					+ "			InitializationError error= new InitializationError(String.format(\n"
					+ "					CONSTRUCTOR_ERROR_FORMAT, simpleName, simpleName));\n"
					+ "			return Request.errorReport(fTestClass, error).getRunner();\n",

			"    public boolean isReadOnly() throws HsqlException {\n" + "\n"
					+ "        Object info = getAttribute(Session.INFO_CONNECTION_READONLY);\n" + "\n"
					+ "        isReadOnly = ((Boolean) info).booleanValue();\n" + "\n" + "        return isReadOnly;\n",

			" \n" + "        boolean response = warehouseDialog.getResponseBoolean();\n"
					+ "        remove(warehouseDialog);\n" + "        return response;\n",

			"			else if ( returnType.equals(\"F\") )\n" + "				opcode = FRETURN;\n"
					+ "			else if ( returnType.equals(\"J\") )  //long\n" + "				opcode = LRETURN;\n" + "\n"
					+ "			cv.visitInsn(opcode);\n",

			"    String channelId;\n" + "    \n" + "    if (version==1) {\n"
					+ "      dataServiceId = (String)in.readObject();\n" + "      channelId=\"\"+in.readInt();\n",

			"	@Override\n" + "	public Description getDescription() {\n"
					+ "		Description spec= Description.createSuiteDescription(getName());\n"
					+ "		List<Method> testMethods= fTestMethods;\n" + "		for (Method method : testMethods)\n"
					+ "				spec.addChild(methodDescription(method));\n",

			"\n" + "            while (classNames.hasNext()) {\n"
					+ "                clsName         = (String) classNames.next();\n"
					+ "                clsCat          = ns.getCatalogName(clsName);\n"
					+ "                clsSchem        = ns.getSchemaName(clsName);\n",

			"\n" + "        String[] texts = new String[messages.length];\n"
					+ "        ImageIcon[] images = new ImageIcon[messages.length];\n"
					+ "        for (int i = 0; i < messages.length; i++) {\n"
					+ "            String ID = messages[i].getMessageID();\n",

			"\n"
					+ "      mProgramTable.changeSelection(row, 0, false, false);\n"
					+ "\n"
					+ "      Program p = (Program) mProgramTableModel.getValueAt(row, 1);\n"
					+ "\n"
					+ "      JPopupMenu menu = devplugin.Plugin.getPluginManager().createPluginContextMenu(p, CapturePlugin.getInstance());\n",

			"  /**\n" + "   * Add one zero if neccessary\n" + "   * @param number\n" + "   * @return\n" + "   */\n"
					+ "  private CharSequence addZero(int number) {\n"
					+ "    StringBuilder builder = new StringBuilder();\n" + "    \n" + "    if (number < 10) {\n"
					+ "      builder.append('0');\n" + "    }\n" + "    \n"
					+ "    builder.append(Integer.toString(number));\n",

			"	@Override\n" + "	public void run(RunNotifier notifier) {\n" + "		TestResult result= new TestResult();\n"
					+ "		result.addListener(createAdaptingListener(notifier));\n" + "		fTest.run(result);\n",

			"        t.checkColumnsMatch(tc.core.mainColArray, tc.core.refTable,\n"
					+ "                            tc.core.refColArray);\n" + "        session.commit();\n" + "\n"
					+ "        TableWorks tableWorks = new TableWorks(session, t);\n",

			"        @Override\n"
					+ "        public void mousePressed(MouseEvent e) {\n"
					+ "            if (f.getDesktopPane() == null || f.getDesktopPane().getDesktopManager() == null) {\n"
					+ "                return;\n"
					+ "            }\n"
					+ "            loc = SwingUtilities.convertPoint((Component) e.getSource(), e.getX(), e.getY(), null);\n"
					+ "            f.getDesktopPane().getDesktopManager().beginDraggingFrame(f);\n",

			"	/**\n" + "		Translate bsh.Modifiers into ASM modifier bitflags.\n" + "	*/\n"
					+ "	static int getASMModifiers( Modifiers modifiers ) \n" + "	{\n" + "		int mods = 0;\n"
					+ "		if ( modifiers == null )\n" + "			return mods;\n" + "\n"
					+ "		if ( modifiers.hasModifier(\"public\") )\n" + "			mods += ACC_PUBLIC;\n",

			"    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {\n"
					+ "\n"
					+ "        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);\n"
					+ "        \n"
					+ "        String str;\n"
					+ "        \n"
					+ "        if (value instanceof DeviceIf) {\n"
					+ "            DeviceIf device = (DeviceIf)value; \n",

			"		public String compact(String message) {\n"
					+ "			if (fExpected == null || fActual == null || areStringsEqual())\n"
					+ "				return Assert.format(message, fExpected, fActual);\n" + "\n" + "			findCommonPrefix();\n"
					+ "			findCommonSuffix();\n",

			"\n" + "        classNames = classNameSet.iterator();\n" + "\n"
					+ "        while (classNames.hasNext()) {\n"
					+ "            className = (String) classNames.next();\n"
					+ "            methods   = iterateRoutineMethods(className, andAliases);\n",

			"    /**\n" + "     * Generates a color chip image and stores it in memory.\n" + "     * \n"
					+ "     * @param gc The GraphicsConfiguration is needed to create images that are\n"
					+ "     *            compatible with the local environment.\n"
					+ "     * @param c The color of the color chip to create.\n" + "     */\n"
					+ "    private void loadColorChip(GraphicsConfiguration gc, Color c) {\n"
					+ "        BufferedImage tempImage = gc.createCompatibleImage(11, 17);\n"
					+ "        Graphics g = tempImage.getGraphics();\n" + "        if (c.equals(Color.BLACK)) {\n"
					+ "            g.setColor(Color.WHITE);\n",

			"      \n" + "      out.writeObject(device.getDriver().getClass().getName());\n"
					+ "      out.writeObject(device.getName());\n" + "      \n" + "      device.writeData(out);\n",

			"        \n"
					+ "        File data = new File(Plugin.getPluginManager().getTvBrowserSettings().getTvBrowserUserHome()  + File.separator + \n"
					+ "                \"CaptureDevices\" + File.separator + mCount + \".dat\");\n" + "        \n"
					+ "        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(data));\n"
					+ "        \n" + "        dev.writeData(stream);\n",

			"	private static Class<?>[] getAnnotatedClasses(Class<?> klass) throws InitializationError {\n"
					+ "		SuiteClasses annotation= klass.getAnnotation(SuiteClasses.class);\n"
					+ "		if (annotation == null)\n"
					+ "			throw new InitializationError(String.format(\"class '%s' must have a SuiteClasses annotation\", klass.getName()));\n"
					+ "		return annotation.value();\n",

			"\n" + "        for (int j = 0; j < fieldcount; j++) {\n"
					+ "            int i = Column.compare(session.database.collation, a[cols[j]],\n"
					+ "                                   b[cols[j]], coltypes[cols[j]]);\n" + "\n"
					+ "            if (i != 0) {\n" + "                return i;\n" + "            }\n" + "        }\n"
					+ "\n" + "        return 0;\n",

			"    /**\n" + "     * Closes all panels, changes the background and shows the main menu.\n" + "     */\n"
					+ "    public void returnToTitle() {\n"
					+ "        // TODO: check if the GUI object knows that we're not\n"
					+ "        // inGame. (Retrieve value of GUI::inGame.)  If GUI thinks\n"
					+ "        // we're still in the game then log an error because at this\n"
					+ "        // point the GUI should have been informed.\n" + "        closeMenus();\n"
					+ "        removeInGameComponents();\n" + "        showMainPanel();\n",

			"	/**\n" + "		subsequent imports override earlier ones\n" + "	*/\n"
					+ "    public void	importPackage(String name)\n" + "    {\n" + "		if(importedPackages == null)\n"
					+ "			importedPackages = new Vector();\n" + "\n"
					+ "		// If it exists, remove it and add it at the end (avoid memory leak)\n"
					+ "		if ( importedPackages.contains( name ) )\n" + "			importedPackages.remove( name );\n" + "\n"
					+ "		importedPackages.addElement(name);\n",

			"        \n" + "        if(dataServiceId.compareTo(cmpDataServiceId) != 0) {\n"
					+ "          return false;\n" + "        }\n" + "        \n"
					+ "        String country = getCountry();\n" + "        String cmpCountry = cmp.getCountry();\n",

			"	public void filter(Filter filter) throws NoTestsRemainException {\n"
					+ "		for (Iterator<Method> iter= fTestMethods.iterator(); iter.hasNext();) {\n"
					+ "			Method method= iter.next();\n" + "			if (!filter.shouldRun(methodDescription(method)))\n"
					+ "				iter.remove();\n" + "		}\n" + "		if (fTestMethods.isEmpty())\n"
					+ "			throw new NoTestsRemainException();\n",

			"        /* fredt - in FK constraints column lists for iColMain and iColRef have\n"
					+ "           identical sets to visible columns of iMain and iRef respectively\n"
					+ "           but the order of columns can be different and must be preserved\n" + "         */\n"
					+ "        core.mainColArray = mainCols;\n"
					+ "        core.colLen       = core.mainColArray.length;\n"
					+ "        core.refColArray  = refCols;\n",

			"    /**\n" + "    * Adds a message to the list of messages that need to be displayed on the GUI.\n"
					+ "    * @param message The message to add.\n" + "    */\n"
					+ "    public synchronized void addMessage(GUIMessage message) {\n"
					+ "        if (getMessageCount() == MESSAGE_COUNT) {\n" + "            messages.remove(0);\n"
					+ "        }\n" + "        messages.add(message);\n" + "\n"
					+ "        freeColClient.getCanvas().repaint(0, 0, getWidth(), getHeight());\n",

			"private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2)\n" + "{\n"
					+ "   switch(hiByte)\n" + "   {\n" + "      case 0:\n"
					+ "         return ((jjbitVec0[i2] & l2) != 0L);\n" + "      default : \n"
					+ "         if ((jjbitVec1[i1] & l1) != 0L)\n" + "            return true;\n"
					+ "         return false;\n",

			"    private static Date correctTimeZone(final Date date) {\n" + "       Date ret=date;\n"
					+ "       if(java.util.TimeZone.getDefault().useDaylightTime()){\n"
					+ "            if(java.util.TimeZone.getDefault().inDaylightTime(date))\n"
					+ "                ret.setTime(date.getTime()+1*60*60*1000);\n" + "        }\n"
					+ "        return ret;\n",

			"	@Override\n" + "	public String getMessage() {\n" + "		StringBuilder builder= new StringBuilder();\n"
					+ "		if (fMessage != null)\n" + "			builder.append(fMessage);\n"
					+ "		builder.append(\"arrays first differed at element \");\n",

			"    String getStateString() {\n" + "\n" + "        int state = getState();\n" + "\n"
					+ "        switch (state) {\n" + "\n" + "            case DATABASE_CLOSING :\n"
					+ "                return \"DATABASE_CLOSING\";\n" + "\n" + "            case DATABASE_ONLINE :\n"
					+ "                return \"DATABASE_ONLINE\";\n",

			"    public boolean displayTileCursor(Tile tile, int canvasX, int canvasY) {\n"
					+ "        if (currentMode == ViewMode.VIEW_TERRAIN_MODE) {\n"
					+ "\n"
					+ "            Position selectedTilePos = gui.getSelectedTile();\n"
					+ "            if (selectedTilePos == null)\n"
					+ "                return false;\n"
					+ "\n"
					+ "            if (selectedTilePos.getX() == tile.getX() && selectedTilePos.getY() == tile.getY()) {\n"
					+ "                TerrainCursor cursor = gui.getCursor();\n",

			"private final int jjMoveStringLiteralDfa18_0(long old1, long active1, long old2, long active2)\n" + "{\n"
					+ "   if (((active1 &= old1) | (active2 &= old2)) == 0L)\n"
					+ "      return jjStartNfa_0(16, 0L, old1, old2); \n"
					+ "   try { curChar = input_stream.readChar(); }\n" + "   catch(java.io.IOException e) {\n"
					+ "      jjStopStringLiteralDfa_0(17, 0L, active1, active2);\n",

			"    /**\n" + "     * Get the List of all available Channels\n" + "     * \n"
					+ "     * @return All available Channels\n" + "     */\n"
					+ "    public ElgatoChannel[] getAvailableChannels() {\n"
					+ "        ArrayList<ElgatoChannel> list = new ArrayList<ElgatoChannel>();\n" + "\n"
					+ "        String res = null;\n" + "        try {\n"
					+ "            res = mAppleScript.executeScript(CHANNELLIST);\n",

			"		private Method getParametersMethod() throws Exception {\n"
					+ "			for (Method each : fKlass.getMethods()) {\n"
					+ "				if (Modifier.isStatic(each.getModifiers())) {\n"
					+ "					Annotation[] annotations= each.getAnnotations();\n"
					+ "					for (Annotation annotation : annotations) {\n"
					+ "						if (annotation.annotationType() == Parameters.class)\n" + "							return each;\n"
					+ "					}\n" + "				}\n" + "			}\n"
					+ "			throw new Exception(\"No public static parameters method on class \"\n"
					+ "					+ getName());\n",

			"\n" + "        Node r = x.getRight();\n" + "\n" + "        if (r != null) {\n" + "            x = r;\n"
					+ "\n" + "            Node l = x.getLeft();\n",

			"        InGameInputHandler inGameInputHandler = freeColClient.getInGameInputHandler();\n" + "\n"
					+ "        freeColClient.getClient().setMessageHandler(inGameInputHandler);\n"
					+ "        gui.setInGame(true);\n",

			"    /**\n" + "     * Applies this action.\n" + "     * \n"
					+ "     * @param e The <code>ActionEvent</code>.\n" + "     */\n"
					+ "    public void actionPerformed(ActionEvent e) {\n"
					+ "        final Game game = freeColClient.getGame();\n"
					+ "        final Map map = game.getMap();\n" + "\n"
					+ "        Parameters p = showParametersDialog();\n",

			"    public ActionMenu getButtonAction() {\n"
					+ "        AbstractAction action = new AbstractAction() {\n"
					+ "\n"
					+ "            public void actionPerformed(ActionEvent evt) {\n"
					+ "                showDialog();\n"
					+ "            }\n"
					+ "        };\n"
					+ "        action.putValue(Action.NAME, mLocalizer.msg(\"CapturePlugin\", \"Capture Plugin\"));\n"
					+ "        action.putValue(Action.SMALL_ICON, createImageIcon(\"mimetypes\", \"video-x-generic\", 16));\n",

			"			Description description= Description.createSuiteDescription(name);\n" + "			int n= ts.testCount();\n"
					+ "			for (int i= 0; i < n; i++)\n" + "				description.addChild(makeDescription(ts.testAt(i)));\n",

			"\n" + "        if (expression.exprType != VALUE && expression.exprType != COLUMN\n"
					+ "                && expression.exprType != FUNCTION\n"
					+ "                && expression.exprType != ALTERNATIVE\n"
					+ "                && expression.exprType != CASEWHEN\n"
					+ "                && expression.exprType != CONVERT) {\n"
					+ "            StringBuffer temp = new StringBuffer();\n" + "\n"
					+ "            ddl = temp.append('(').append(ddl).append(')').toString();\n" + "        }\n" + "\n"
					+ "        return ddl;\n",

			"    private synchronized void purgeOldMessagesFromMessagesToIgnore(int thisTurn) {\n"
					+ "        List<String> keysToRemove = new ArrayList<String>();\n"
					+ "        for (Entry<String, Integer> entry : messagesToIgnore.entrySet()) {\n"
					+ "            if (entry.getValue().intValue() < thisTurn - 1) {\n"
					+ "                if (logger.isLoggable(Level.FINER)) {\n"
					+ "                    logger.finer(\"Removing old model message with key \" + entry.getKey() + \" from ignored messages.\");\n"
					+ "                }\n" + "                keysToRemove.add(entry.getKey());\n",

	};

	public static final int[] scores = {

	1,

	0,

	1,

	0,

	0,

	1,

	0,

	1,

	0,

	0,

	0,

	0,

	1,

	0,

	1,

	1,

	0,

	1,

	0,

	0,

	0,

	1,

	0,

	0,

	1,

	1,

	0,

	1,

	0,

	1,

	0,

	0,

	0,

	0,

	1,

	1,

	1,

	1,

	1,

	1,

	1,

	1,

	0,

	0,

	0,

	0,

	0,

	0,

	0,

	0,

	0,

	1,

	1,

	0,

	1,

	0,

	0,

	1,

	0,

	0,

	0,

	0,

	1,

	0,

	0,

	1,

	0,

	0,

	1,

	1,

	0,

	1,

	0,

	0,

	0,

	0,

	1,

	1,

	0,

	0,

	0,

	0,

	1,

	0,

	0,

	1,

	1,

	0,

	0,

	0,

	1,

	0,

	1,

	0,

	0,

	0,

	1,

	1,

	1,

	1,

	};

	public static List<Snippet> getSnippets()
	{
		LinkedList<Snippet> list = new LinkedList<Snippet>();

		for (int i = 0; i < snippets.length; i++)
		{
			list.addLast(new Snippet(snippets[i], i));
		}

		return list;
	}

}
