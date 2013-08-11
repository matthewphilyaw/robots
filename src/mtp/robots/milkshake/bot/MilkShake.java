package mtp.robots.milkshake.bot;

import mtp.robots.milkshake.analytics.*;
import mtp.robots.milkshake.targeting.*;
import mtp.robots.milkshake.util.*;
import robocode.*;

import java.awt.Graphics2D;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;
import java.util.UUID;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;

public class MilkShake extends AdvancedRobot {
    static final int LAST_HEADING_SIZE = 3;
    static final UUID battleId = UUID.randomUUID();

    static final List<BulletInfo> bulletsFired = new ArrayList<BulletInfo>();
    static final Object bulletsFiredLock = new Object();
    static final Object diGraphLock = new Object();
    static final DirectedGraph<RVertexData, REdgeDataPrintable> g = new DirectedGraph<RVertexData, REdgeDataPrintable>();
    static int maxEdgeVisit = 0;

    final UUID roundId = UUID.randomUUID();
    final List<BulletInfo> bulletsThatHit = new ArrayList<BulletInfo>();
    final RingBuffer<RVertexData> lastVertices = new RingBuffer<RVertexData>(LAST_HEADING_SIZE);

    TargetingData td;
    boolean fire = false;
    TargetingPrediction solutionToFire;

    public void run() {
        this.setAdjustGunForRobotTurn(true);
        this.setAdjustRadarForGunTurn(true);

        while (true) {
            waitFor(new RadarTurnCompleteCondition(this));
            setTurnRadarRight(360);
            setAhead(100);
        }
    }

    public void onHitWall(HitWallEvent e) {
        this.setTurnRight(e.getBearing());
        waitFor(new TurnCompleteCondition(this));
        this.setTurnRight(180);
        waitFor(new TurnCompleteCondition(this));
        this.setAhead(50);
        this.setTurnRight(90);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        updateGraphPrintable(g, new RVertexData(e.getHeading(), -1, RoundingMode.HALF_UP));

        if (this.getGunTurnRemaining() > 0)
            return;

        if (this.fire && !(this.getGunHeat() > 0)) {
            this.fire = false;
            Bullet b = this.setFireBullet(Rules.MAX_BULLET_POWER);
            if (solutionToFire == null) { System.out.println("solution to fire is null"); return; }
            if (b == null) { System.out.println("bullet is null"); return; }
            synchronized (bulletsFiredLock) {
                bulletsFired.add(new BulletInfo(b.hashCode(),
                                 solutionToFire,
                                 MilkShake.battleId,
                                 this.roundId));
            }
        }

        try {
            td = TargetingData.GenerateTargetingData(this, e, 30);
            //out.println(td);

            List<TargetingPrediction> solutions = td.getTargetingSolutions(1);
            if (solutions.size() > 0) {
            /*int index = solutions.size() - 1;
                if (solutions.size() > 4)
                    index = solutions.size() - 4;
                this.solutionToFire = solutions.get(index);*/
                this.solutionToFire = solutions.get(0);
                this.setTurnGunRightRadians(solutions.get(0).getNormalizedToCannonAngle());
                this.fire = true;
            }
            else {
                this.setTurnGunRightRadians(this.getHeadingRadians() + e.getBearingRadians());
            }
        }
        catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    public void onBulletHit(BulletHitEvent e) {
        synchronized (bulletsFiredLock) {
            for (BulletInfo bi : bulletsFired) {
                if (!bi.doesBulletMatch(e.getBullet(), MilkShake.battleId, this.roundId)) continue;
                bi.setHit();
                bulletsThatHit.add(bi);
                solutionToFire = null;
                break;
            }
        }
    }

/*    public void onRoundEnded(RoundEndedEvent event) {
        double tTicks = 0;
        double avgTicks;

        for (BulletInfo bi : bulletsThatHit) {
            tTicks += bi.getPrediction().getTime();
            System.out.println(bi.getPrediction().getTime());
        }

        avgTicks = tTicks / bulletsThatHit.size();
        System.out.print(String.format("avg ticks for round: %f\ntotal tick: %f\ntotal hits: %d\ntotal bullets (per battle): %d\n",
                                       avgTicks,
                                       tTicks,
                                       bulletsThatHit.size(),
                                       bulletsFired.size()));
    }*/

    public void onPaint(Graphics2D g) {
        if (td == null) return;
        if (this.getTime() - td.getTimeCreated() > 5) { return; }
        td.renderPredictions(g);
    }

    @Override
    public void onBattleEnded(BattleEndedEvent event) {
        try {
            File f = new File("graph.dot");
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.renderGraphPrintable(g, maxEdgeVisit - (maxEdgeVisit / 3)));
            bw.flush();
            bw.close();

            File r = new File("graph-not-filtered.dot");
            FileWriter rw = new FileWriter(r.getAbsoluteFile());
            BufferedWriter cw = new BufferedWriter(rw);
            cw.write(g.toString());
            cw.flush();
            cw.close();
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
    }

    private void updateGraphPrintable(DirectedGraph<RVertexData, REdgeDataPrintable> g, RVertexData vert) {
        synchronized (diGraphLock) {
            g.addVertex(vert);
            if (lastVertices.getItems().size() > 0 && !vert.equals(lastVertices.getItems().get(0))) {
                RVertexData lastVertData = lastVertices.getItems().get(0);
                try {
                    if (!g.getEdges(lastVertData).containsKey(vert)) g.addEdge(lastVertData, vert, new REdgeDataPrintable());
                    g.getEdges(lastVertData).get(vert).add(lastVertices.getFnvHash(), lastVertices.toString());
                    if ((Integer)g.getEdges(lastVertData).get(vert).getPaths().get(lastVertices.getFnvHash())[1] > maxEdgeVisit)
                        maxEdgeVisit = (Integer)g.getEdges(lastVertData).get(vert).getPaths().get(lastVertices.getFnvHash())[1];

                } catch (Exception ex) { System.out.println(ex.getMessage()); }
            }

            if (lastVertices.getItems().size() > 0 && !vert.equals(lastVertices.getItems().get(0))) {
                lastVertices.add(vert);
            }
            else if (lastVertices.getItems().size() <= 0)
                lastVertices.add(vert);
        }
    }

    private void updateGraph(DirectedGraph<RVertexData, REdgeData> g, RVertexData vert) {
        synchronized (diGraphLock) {
            g.addVertex(vert);
            if (lastVertices.getItems().size() > 0 && !vert.equals(lastVertices.getItems().get(0))) {
                RVertexData lastVertData = lastVertices.getItems().get(0);
                try {
                    if (!g.getEdges(lastVertData).containsKey(vert)) g.addEdge(lastVertData, vert, new REdgeData());
                    g.getEdges(lastVertData).get(vert).add(lastVertices.getFnvHash());
                    if (g.getEdges(lastVertData).get(vert).getPaths().get(lastVertices.getFnvHash()) > maxEdgeVisit)
                        maxEdgeVisit = g.getEdges(lastVertData).get(vert).getPaths().get(lastVertices.getFnvHash());

                } catch (Exception ex) { System.out.println(ex.getMessage()); }
            }
            lastVertices.add(vert);
        }
    }

    private String renderGraph(DirectedGraph<RVertexData, REdgeData> g, int visitThreshold) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        sb.append("    graph [layout=neato bgcolor=black fontcolor=white fontsize=40 overlap=prism sep=1 rankdir=LR label=");
        sb.append(visitThreshold);
        sb.append("]\n");
        sb.append("    node [color=white, fontcolor=white]\n");
        sb.append("    edge [color=white splines=ortho fontcolor=white]\n");

        for (Map.Entry<RVertexData, Map<RVertexData, REdgeData>> vert : g.getVertices().entrySet()) {
            for (Map.Entry<RVertexData, REdgeData> ed : vert.getValue().entrySet()) {
                int sum = 0;
                for (Integer i : ed.getValue().getPaths().values()) {
                    sum += i;
                }
                if (sum < visitThreshold) continue;
                sb.append("    ");
                sb.append(vert.getKey().toString());
                sb.append(" -> ");
                sb.append(ed.getKey().toString());
                sb.append(" [label=\"");
                sb.append(sum);
                sb.append("\"]\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private String renderGraphPrintable(DirectedGraph<RVertexData, REdgeDataPrintable> g, int visitThreshold) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        sb.append("    graph [layout=dot bgcolor=black fontcolor=white fontsize=40 label=");
        sb.append(visitThreshold);
        sb.append("]\n");
        sb.append("    node [color=white, fontcolor=white]\n");
        sb.append("    edge [color=white splines=ortho fontcolor=white]\n");

        for (Map.Entry<RVertexData, Map<RVertexData, REdgeDataPrintable>> vert : g.getVertices().entrySet()) {
            for (Map.Entry<RVertexData, REdgeDataPrintable> ed : vert.getValue().entrySet()) {
                Integer sum = 0;
                Object name = "";
                for (Object[] i : ed.getValue().getPaths().values()) {
                    if ((Integer)i[1] > sum) { sum = (Integer)i[1]; name = i[0]; }
                }
                if (sum < visitThreshold) {
                    continue;
                    /*sb.append("    ");
                    sb.append(vert.getKey().toString());
                    sb.append(" -> ");
                    sb.append(ed.getKey().toString());
                    sb.append(" [color=\"#ff000040\"]");*/
                }
                else {
                    sb.append("    ");
                    sb.append(vert.getKey().toString());
                    sb.append(" -> ");
                    sb.append(ed.getKey().toString());
                    sb.append(" [label=\"");
                    sb.append(name.toString());
                    sb.append(":");
                    sb.append(sum.toString());
                    sb.append("\"]\n");
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
