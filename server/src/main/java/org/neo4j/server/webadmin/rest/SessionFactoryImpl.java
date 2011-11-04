/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.server.webadmin.rest;

import org.neo4j.server.database.Database;
import org.neo4j.server.webadmin.console.GremlinSession;
import org.neo4j.server.webadmin.console.PqlSession;
import org.neo4j.server.webadmin.console.ScriptSession;

import javax.servlet.http.HttpSession;

public class SessionFactoryImpl implements SessionFactory
{
    private HttpSession httpSession;

    public SessionFactoryImpl( HttpSession httpSession )
    {
        this.httpSession = httpSession;
    }

    @Override
    public ScriptSession createSession( String engineName, Database database )
    {
        if ( engineName.equals( "cypher" ) )
        {
            return new PqlSession( database.graph );
        }
        else
        {
            Object session = httpSession.getAttribute( "consoleSession" );
            if ( session == null )
            {
                session = new GremlinSession( database );
                httpSession.setAttribute( "consoleSession", session );
            }
            return (ScriptSession) session;
        }
    }
}
