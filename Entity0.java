public class Entity0 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity0()
    {
    	
    	for(int i=0;i<NetworkSimulator.NUMENTITIES;i++){
    		for(int j =0;j<NetworkSimulator.NUMENTITIES;j++){
    	distanceTable[i][j]=999;
    	
    		}
    	}
    	distanceTable[0][0]=0;
    	distanceTable[1][1]=1;
    	distanceTable[2][2]=3;
    	distanceTable[3][3]=7;
    	
    	int[] arr= new int[4];
    	arr[0]=distanceTable[0][0];
    	arr[1]=distanceTable[1][1];
    	arr[2]=distanceTable[2][2];
    	arr[3]=distanceTable[3][3];
    	System.out.println("\nInitial table at node 0:");
    	printDT();
    	System.out.print("sending out DV:");
    	for (int i=0;i<arr.length;i++){
    		System.out.print(arr[i] + " ");
    	}
    	System.out.println("\n\n");
    	Packet p01 = new Packet(0,1,arr);
    	Packet p02 = new Packet(0,2,arr);
    	Packet p03 = new Packet(0,3,arr);
        NetworkSimulator.toLayer2(p01);
        NetworkSimulator.toLayer2(p02);
        NetworkSimulator.toLayer2(p03);
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {        
    	
    	int[] neighbors={1,2,3};
    	int[] minlist = new int[4];
    	int src=p.getSource();
    	int[] update1 = new int[4];
    	
    	boolean update=false;
    	for(int i=0;i<neighbors.length;i++){
    		if (distanceTable[neighbors[i]][src]>NetworkSimulator.cost[0][src] + p.getMincost(neighbors[i])){
    			distanceTable[neighbors[i]][src]=NetworkSimulator.cost[0][src] + p.getMincost(neighbors[i]);
    			update1[neighbors[i]]=distanceTable[neighbors[i]][src];
    			
    	}
    	}	
    	
    	int min=0;
    	for(int k=0;k<NetworkSimulator.NUMENTITIES;k++){
    		min=999;
    		for (int m=0;m<NetworkSimulator.NUMENTITIES;m++){
    		if (min>distanceTable[k][m]){
    			min=distanceTable[k][m];
    		}
    		minlist[k]=min;
    		
    		
    	   }
    		if (minlist[k]!=0 && update1[k]==minlist[k] ){
    			update=true;
    		}
    	}
    	
    		
    	
    		
    	
    	minlist[0]=0;
    	if (update){
    		System.out.println("Node 0 table updated:");
    		printDT();
    		System.out.print("\nsending out DV:");
    		for (int i=0;i<minlist.length;i++){
        		System.out.print(minlist[i] + " ");
        	}
    		System.out.println("\n\n");
    		Packet p0_1 = new Packet(0,1,minlist);
        	Packet p0_2 = new Packet(0,2,minlist);
        	Packet p0_3 = new Packet(0,3,minlist);
            NetworkSimulator.toLayer2(p0_1);
            NetworkSimulator.toLayer2(p0_2);
            NetworkSimulator.toLayer2(p0_3);
    	}
    	else{
    		System.out.println("Node 0 table not updated:");
    		printDT();
    		System.out.println("\n\n");
    	}
    	
    	 
    	
    	
    	
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   1   2   3");
        System.out.println("----+------------");
        for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
