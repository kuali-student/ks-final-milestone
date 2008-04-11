package org.kuali.student.brms.repository.drools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.jcr.RepositoryException;

import org.drools.compiler.DroolsError;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.compiler.PackageBuilderErrors;
import org.drools.lang.descr.PackageDescr;
import org.drools.repository.AssetItem;
import org.drools.repository.PackageItem;
import org.drools.util.ChainedProperties;
import org.kuali.student.brms.repository.BRMSRepositoryException;
import org.kuali.student.brms.repository.BuilderResult;
import org.kuali.student.brms.repository.BuilderResultList;
import org.kuali.student.brms.repository.Rule;
import org.kuali.student.brms.repository.RuleImpl;
import org.kuali.student.brms.repository.RuleSet;
import org.kuali.student.brms.repository.RuleSetImpl;

public class DroolsUtil 
{
    /**
     * Build a rule from an Drools repository item.
     * 
     * @param item Drools repository item
     * @return A rule
     * @throws BRMSRepositoryException
     */
	public static RuleImpl buildRule( AssetItem item )
		throws BRMSRepositoryException
    {
    	RuleImpl rule = new RuleImpl( item.getUUID(), item.getName() );
    	rule.setContent( item.getContent() );
    	rule.setBinaryContent( item.getBinaryContentAsBytes() );
    	rule.setFormat( item.getFormat() );
    	rule.setVersionNumber( item.getVersionNumber() );
    	rule.setStatus( ( item.getState() == null ? "Draft" : item.getState().getName() ) );
    	rule.setDescription( item.getDescription() );
		rule.setCheckinComment( item.getCheckinComment() );
		rule.setEffectiveDate( item.getDateEffective() );
		rule.setExpiryDate( item.getDateExpired() );
		rule.setCreatedDate( item.getCreatedDate() );
		rule.setLastModifiedDate( item.getLastModified() );
		rule.setArchived( item.isArchived() );

		try
		{
			rule.setHistorical( item.isHistoricalVersion() );
			if ( item.isHistoricalVersion() )
			{
				rule.setVersionSnapshotUUID( item.getVersionSnapshotUUID() );
			}
		}
		catch( RepositoryException e )
		{
			throw new BRMSRepositoryException( "Unable to set rule historical version", e );
		}
		
		return rule;
    }

	/**
     * Build a rule from from history from an Drools repository item.
     * 
     * Note: AssetItem.getFormat(), AssetItem.getLastModified(), 
     * AssetItem.isArchived() throws exception when creating from history
     * 
     * @param item Drools repository history item
     * @return A history rule
     * @throws BRMSRepositoryException
	 */
    public static Rule buildHistoricalRule( AssetItem item )
		throws BRMSRepositoryException
	{
    	RuleImpl rule = new RuleImpl( item.getUUID(), item.getName() );
    	rule.setContent( item.getContent() );
    	rule.setBinaryContent( item.getBinaryContentAsBytes() );
		// item.getFormat() throws exception when creating from history
    	rule.setFormat( null );
    	rule.setVersionNumber( item.getVersionNumber() );
    	rule.setStatus( ( item.getState() == null ? "Draft" : item.getState().getName() ) );
		rule.setDescription( item.getDescription() );
		rule.setCheckinComment( item.getCheckinComment() );
		rule.setEffectiveDate( item.getDateEffective() );
		rule.setExpiryDate( item.getDateExpired() );
		rule.setCreatedDate( item.getCreatedDate() );
		// item.getLastModified() throws exception when creating from history
		rule.setLastModifiedDate( null );
		// item.isArchived() throws exception when creating from history
		rule.setArchived( false );
		rule.setVersionSnapshotUUID( item.getVersionSnapshotUUID() );

		try
		{
			rule.setHistorical( item.isHistoricalVersion() );
		}
		catch( RepositoryException e )
		{
			throw new BRMSRepositoryException( "Unable to set rule historical version", e );
		}
		
		return rule;
	}
    
    /**
     * Build a rule set from an Drools repository item.
     * 
     * @param item Drools repository package item
     * @return A rule set
     * @throws BRMSRepositoryException
     */
    public static RuleSet buildRuleSet( PackageItem pkg )
    	throws BRMSRepositoryException
    {
		RuleSetImpl ruleSet = new RuleSetImpl( 
				pkg.getUUID(), 
				pkg.getName() );
		ruleSet.setVersionNumber( pkg.getVersionNumber() );
    	ruleSet.setStatus( ( pkg.getState() == null ? "Draft" : pkg.getState().getName() ) );
		ruleSet.setDescription( pkg.getDescription() );
		ruleSet.setCheckinComment( pkg.getCheckinComment() );
		ruleSet.setCreatedDate( pkg.getCreatedDate() );
		ruleSet.setLastModifiedDate( pkg.getLastModified() );
		ruleSet.setArchived( pkg.isArchived() );
		ruleSet.setSnapshot( pkg.isSnapshot() );
		
		ruleSet.setCompiledRuleSet( pkg.getCompiledPackageBytes() );
		
		try
		{
			ruleSet.setHistorical( pkg.isHistoricalVersion() );
		}
		catch( RepositoryException e )
		{
			throw new BRMSRepositoryException( "Unable to set rule set historical version", e );
		}
		
		List<Rule> list = new ArrayList<Rule>();
		for( Iterator<AssetItem> it = pkg.getAssets(); it.hasNext(); )
		{
			AssetItem item = it.next();
			Rule rule = buildRule( item );
			list.add( rule );
		}
		ruleSet.setRules( list );

		return ruleSet;
    }

    public static List<BuilderResult> generateBuilderResults( PackageBuilderErrors errors, org.drools.repository.VersionableItem item ) 
    {
    	List<BuilderResult> result = new ArrayList<BuilderResult>();
    	DroolsError[] dr = errors.getErrors();
    	for( int i=0; i<dr.length; i++ )
    	{
            String uuid = item.getUUID();
            String name = item.getName();
            String format = item.getFormat();
            String message = dr[i].getMessage();
            BuilderResult br = new BuilderResult( uuid, name, format, message );
            result.add( br );
    	}
        return result;
    }
    
    public static InputStream getBinaryPackage( org.drools.rule.Package pkg )
    	throws IOException
    {
    	ObjectOutputStream oos = null;
    	try
        {
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        oos = new ObjectOutputStream( baos );
	        oos.writeObject( pkg );
	        return new ByteArrayInputStream( baos.toByteArray() );
        }
        finally
        {
	        if ( oos != null ) oos.flush();
	        if ( oos != null ) oos.close();
        }
    }

    public static BuilderResultList compile( PackageItem pkg ) throws IOException, DroolsParserException
    {
        PackageBuilder builder = createPackageBuilder();
        builder.addPackage( new PackageDescr( pkg.getName() ) );
        String drl = pkg.getHeader();
        builder.addPackageFromDrl( new StringReader( drl ) );
    	
        List<BuilderResult> errors = new ArrayList<BuilderResult>();
    	
        if ( builder.hasErrors() )
    	{
    		List<BuilderResult> l = generateBuilderResults( builder.getErrors(), pkg );
    		errors.addAll( l );
    	}
        
        for( Iterator<AssetItem> it = pkg.getAssets(); it.hasNext(); )
        {
            AssetItem item = it.next();
        	builder.addPackageFromDrl( new StringReader( item.getContent() ) );
        	if ( builder.hasErrors() )
        	{
        		List<BuilderResult> l = generateBuilderResults( builder.getErrors(), item );
        		errors.addAll( l );
        	}
        }
        BuilderResultList result = new BuilderResultList( builder.getPackage() );
        result.addAll( errors );
        return result;
    }

    public static PackageBuilder createPackageBuilder()
    {
    	ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
        ChainedProperties chainedProperties = new ChainedProperties( 
        		BRMSRepositoryDroolsImpl.class.getClassLoader(), // pass this as it searches currentThread anyway
                "packagebuilder.conf",
                false ); // false means it ignores any default values
		// the default compiler. This is nominally JANINO but can be overridden by setting drools.dialect.java.compiler to ECLIPSE
		Properties properties = new Properties();
		properties.setProperty( "drools.dialect.java.compiler",
		chainedProperties.getProperty( "drools.dialect.java.compiler", "JANINO" ) );
		PackageBuilderConfiguration pkgConf = new PackageBuilderConfiguration( properties );
		pkgConf.setClassLoader( parentClassLoader );
    	PackageBuilder builder = new PackageBuilder( pkgConf );
    	return builder;
    }
    
    public static String getDRL( PackageItem pkg )
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append( "package " );
    	sb.append( pkg.getName() );
    	sb.append( "\n" );
    	sb.append( pkg.getHeader() );
    	sb.append( "\n\n" );
    	
    	for( Iterator<AssetItem> it = pkg.getAssets(); it.hasNext(); )
    	{
    		sb.append( it.next().getContent() );
        	sb.append( "\n" );
    	}
    	
    	return sb.toString();
    }
    
}
